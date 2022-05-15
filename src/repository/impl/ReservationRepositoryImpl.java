package repository.impl;

import exceptions.NonExistingEntityException;
import model.entity.Reservation;
import model.entity.Table;
import model.entity.User;
import model.enums.ReservationStatusEnum;
import repository.Repository;
import repository.ReservationRepository;
import security.CurrentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ReservationRepositoryImpl implements ReservationRepository {
    private final Connection connection;

    public ReservationRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Reservation findById(Long id) throws NonExistingEntityException {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM reservation WHERE id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                throw new NonExistingEntityException("There is no reservation with id = "+id);
            }
            return Reservation.mapRow(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Reservation entity) {
        String insertSQL = "INSERT INTO reservation VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            int i = 1;
            statement.setLong(i++,0L);
            setRowWithoutId(entity,statement,i);
            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Reservation entity) throws NonExistingEntityException {
        if (entity.getId() == null || findById(entity.getId()) == null){
            throw  new NonExistingEntityException("There is no such reservation ,persisted in database");
        }
        String updateSQL = "UPDATE reservation " +
                "SET table_id = ?, user_id = ? , full_name = ? , mobile = ? , date_of_reservation = ? , " +
                "created = ? ,modified = ? , content = ? , status = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateSQL);
            int i = 1;
            i = setRowWithoutId(entity,statement,i);
            statement.setLong(i,entity.getId());
            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int setRowWithoutId(Reservation entity, PreparedStatement statement, int i) throws SQLException {
        statement.setLong(i++,entity.getTable().getId());
        statement.setLong(i++,entity.getUser().getId());
        statement.setString(i++,entity.getFullName());
        statement.setString(i++,entity.getMobile());
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getDateTimeOfReservation()));
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getCreated()));
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getModified()));
        statement.setString(i++,entity.getContent());
        statement.setString(i++,entity.getStatus().toString());
        return i;
    }



    @Override
    public Reservation deleteById(Long id) throws NonExistingEntityException {
        var deleted = findById(id);
        String deleteSQL =  "DELETE FROM reservation " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setLong(1,id);
            statement.executeUpdate();
            return deleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Reservation> findAll()  {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservation");
            ResultSet resultSet = statement.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()){
                reservations.add(Reservation.mapRow(resultSet));
            }
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public long count(String table) {
        return 0;
    }

    @Override
    public List<Long> findAllTablesReservedByCurrentUser(Long userId) {
        String sql = "SELECT table_id FROM reservation WHERE user_id = ? ";
        try {


            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,userId);
            ResultSet resultSet = statement.executeQuery();
            List<Long> tableId = new ArrayList<>();
            while (resultSet.next()){
                tableId.add(resultSet.getLong("table_id"));
            }
            return tableId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Reservation findByUserAndTableAndStatus(Long tableId) {
        String sql = "SELECT * FROM reservation WHERE user_id = ? AND table_id = ? AND status = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,CurrentUser.getId());
            statement.setLong(2,tableId);
            statement.setString(3, ReservationStatusEnum.NEW.toString());
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                throw  new NonExistingEntityException("There is no reservation in db");
            }
            return Reservation.mapRow(resultSet);
        } catch (SQLException | NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateToFinished(Reservation reservation) {
        String updateSQL = "UPDATE reservation " +
                "SET status = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateSQL);
            statement.setString(1,ReservationStatusEnum.FINISHED.toString());
            statement.setLong(2,reservation.getId());
           statement.executeUpdate() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reservation> findAllNewReservations() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservation WHERE status = ?");
            statement.setString(1,ReservationStatusEnum.NEW.toString());
            ResultSet resultSet = statement.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()){
                reservations.add(Reservation.mapRow(resultSet));
            }
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
