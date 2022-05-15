package repository.impl;

import exceptions.NonExistingEntityException;
import model.entity.OrderItem;
import model.entity.Reservation;
import repository.OrderItemRepository;
import repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class OrderItemRepositoryImpl implements OrderItemRepository {
    private final Connection connection;


    public OrderItemRepositoryImpl(Connection connection) {
        this.connection = connection;

    }

    @Override
    public OrderItem findById(Long id) throws NonExistingEntityException {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM order_item WHERE id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                throw new NonExistingEntityException("There is no order with id = "+id);
            }

            return OrderItem.mapRow(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(OrderItem entity) {
        String insertSQL = "INSERT INTO order_item VALUES (?,?,?,?,?,?,?,?)";
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

    private int setRowWithoutId(OrderItem entity, PreparedStatement statement, int i) throws SQLException {
        statement.setLong(i++,entity.getItem().getId());
        statement.setInt(i++,entity.getQuantity());
        statement.setDouble(i++,entity.getPrice());
        statement.setLong(i++,entity.getOrder().getId());
        statement.setString(i++,entity.getComment());
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getCreated()));
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getModified()));
        return i;
    }

    @Override
    public boolean update(OrderItem entity) throws NonExistingEntityException {
        if (entity.getId() == null || findById(entity.getId()) == null){
            throw  new NonExistingEntityException("There is no such order  ,persisted in database");
        }
        String updateSQL = "UPDATE order_item " +
                "SET item_id = ?, quantity = ? , price = ? , order_id = ? , comment = ? , " +
                "created = ? ,modified = ? " +
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
    public OrderItem deleteById(Long id) throws NonExistingEntityException {
        var deleted = findById(id);
        String deleteSQL =  "DELETE FROM order_item " +
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
    public List<OrderItem> findAll() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_item");
            ResultSet resultSet = statement.executeQuery();
            List<OrderItem> orderItems = new ArrayList<>();
            while (resultSet.next()){
                orderItems.add(OrderItem.mapRow(resultSet));
            }
            return orderItems;
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
    public List<OrderItem> findByOrderId(Long id) {
        List<OrderItem> orderItems = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_item WHERE order_id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                 orderItems.add(OrderItem.mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  orderItems;
    }
}
