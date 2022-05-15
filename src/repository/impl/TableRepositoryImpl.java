package repository.impl;

import exceptions.NonExistingEntityException;
import model.entity.Reservation;
import model.entity.Table;
import model.enums.TableStatusEnum;
import repository.Repository;
import repository.TableRepository;
import security.CurrentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class TableRepositoryImpl implements TableRepository {
    private final Connection connection;

    public TableRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Table findById(Long id) throws NonExistingEntityException {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM table_top WHERE id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                throw new NonExistingEntityException("There is no table with id = "+id);
            }
            return Table.mapRow(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Table entity) {
        String insertSQL = "INSERT INTO table_top VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            int i = 1;
            statement.setLong(i++,0L);
            setRowWithoutId(entity, statement, i);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int setRowWithoutId(Table entity, PreparedStatement statement, int i) throws SQLException {
        statement.setString(i++, entity.getCode());
        statement.setString(i++, entity.getStatus().toString());
        statement.setInt(i++, entity.getCapacity());
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getCreated()));
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getModified()));
        statement.setString(i++, entity.getContent());
        return i;
    }

    @Override
    public boolean update(Table entity) throws NonExistingEntityException {
        if (entity.getId() == null || findById(entity.getId()) == null){
            throw  new NonExistingEntityException("There is no such user ,persisted in database");
        }
        String updateSQL = "UPDATE table_top " +
                "SET code = ?, status = ? , capacity = ? , " +
                "created = ?,modified = ?, content = ? " +
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

    @Override
    public Table deleteById(Long id) throws NonExistingEntityException {
        var deleted = findById(id);
        String deleteSQL =  "DELETE FROM table_top " +
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
    public List<Table> findAll()  {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM table_top");
            ResultSet resultSet = statement.executeQuery();
            List<Table> allTables = new ArrayList<>();
            while (resultSet.next()){
                allTables.add(Table.mapRow(resultSet));
            }
            return allTables;
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
    public List<Table> findAllFreeTables() {
        List<Table> freeTables = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM table_top WHERE status = ?");
            statement.setString(1, TableStatusEnum.FREE.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Table table = Table.mapRow(resultSet);
                freeTables.add(table);
            }
            return freeTables;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return freeTables;

    }

    @Override
    public Table findByCode(String tableCode) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM table_top WHERE code = ?");
            statement.setString(1,tableCode);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                throw new NonExistingEntityException("There is no table with code = "+tableCode);
            }
            return Table.mapRow(resultSet);
        } catch (SQLException | NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateTableToReserved(Table table) {
        String updateSQL = "UPDATE table_top " +
                "SET status = ? , reservation_id = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateSQL);
            statement.setString(1,TableStatusEnum.RESERVED.toString());
            statement.setLong(2,table.getReservation().getId());
            statement.setLong(3,table.getId());
           return statement.executeUpdate() == 1 ;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public boolean updateTableToActive(Table table) {
        String updateSQL = "UPDATE table_top " +
                "SET status = ? , order_id = ? ,reservation_id = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateSQL);
            statement.setString(1,TableStatusEnum.ACTIVE.toString());
            statement.setLong(2,table.getOrder().getId());
            statement.setObject(3,null);
            statement.setLong(4,table.getId());

            return statement.executeUpdate() == 1 ;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public List<Table> findAllActiveTablesByUser() {
        List<Table> allTables = new ArrayList<>();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM table_top WHERE status = ? AND user_id = ?");
            statement.setString(1,TableStatusEnum.ACTIVE.toString());
            statement.setLong(2, CurrentUser.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                allTables.add(Table.mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTables;
    }

    @Override
    public void updateToFree(Table table) {
        String updateSQL = "UPDATE table_top " +
                "SET status = ? , order_id = ?  " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateSQL);
            statement.setString(1,TableStatusEnum.FREE.toString());
            statement.setObject(2,null);
            statement.setLong(3,table.getId());
            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
    }

