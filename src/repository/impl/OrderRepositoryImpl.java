package repository.impl;

import exceptions.NonExistingEntityException;
import model.entity.Order;
import model.entity.OrderItem;
import model.entity.Table;
import model.enums.OrderStatusEnum;
import model.enums.TableStatusEnum;
import repository.OrderItemRepository;
import repository.OrderRepository;
import repository.Repository;
import security.CurrentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static model.enums.OrderStatusEnum.*;

class OrderRepositoryImpl implements OrderRepository {
    private final Connection connection;

    public OrderRepositoryImpl(Connection connection) {
        this.connection = connection;

    }

    @Override
    public Order findById(Long id) throws NonExistingEntityException {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM final_order WHERE id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                throw new NonExistingEntityException("There is no order with id = "+id);
            }


            return  Order.mapRow(resultSet);



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean insert(Order entity) {
        String insertSQL = "INSERT INTO final_order VALUES (?,?,?,?,?,?,?)";
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

    private int setRowWithoutId(Order entity, PreparedStatement statement, int i) throws SQLException {
        statement.setLong(i++,entity.getUser().getId());
        statement.setLong(i++,entity.getTable().getId());
        statement.setDouble(i++,entity.getPrice());
        statement.setString(i++,entity.getStatus().toString());
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getCreated()));
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getModified()));
        return i;
    }

    @Override
    public boolean update(Order entity) throws NonExistingEntityException {
        if (entity.getId() == null || findById(entity.getId()) == null){
            throw  new NonExistingEntityException("There is no such order  ,persisted in database");
        }
        String updateSQL = "UPDATE final_order " +
                "SET user_id = ?,table_id = ? , price = ? , " +
                "status = ? ,created = ?,modified = ? " +
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
    public Order deleteById(Long id) throws NonExistingEntityException {
        var deleted = findById(id);
        String deleteSQL =  "DELETE FROM final_order " +
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
    public List<Order> findAll() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM final_order");
            ResultSet resultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()){
                Order order = Order.mapRow(resultSet);

                orders.add(order);
            }
            return orders;
        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long count(String table) {
        return 0;
    }

     @Override
     public List<Long> findAllTablesOrderByCurrentUser(Long id) {
        String sql = "SELECT table_id FROM final_order WHERE user_id = ? AND status = ? ";
         try {
             PreparedStatement statement = connection.prepareStatement(sql);
             statement.setLong(1,id);
             statement.setString(2, NEW.toString());
             ResultSet resultSet = statement.executeQuery();
             List<Long> tableIds = new ArrayList<>();
             while (resultSet.next()){
                 tableIds.add(resultSet.getLong("table_id"));
             }
             return tableIds;
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return null;
     }

    @Override
    public Order findByUserAndTable(Long tableId) {
        String sql = "SELECT * FROM final_order WHERE user_id = ? AND table_id = ? AND status = ?  ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, CurrentUser.getId());
            statement.setLong(2, tableId);
            statement.setString(3, NEW.toString());
            ResultSet resultSet = statement.executeQuery();
           if(!resultSet.next()){
               throw new NonExistingEntityException("There is no such order in database");
           }
            return Order.mapRow(resultSet);
        } catch (SQLException | NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
        public List<Order> findAllActiveTablesByUser() {
            List<Order> allOrders = new ArrayList<>();
            try {
                PreparedStatement statement =
                        connection.prepareStatement("SELECT * FROM final_order WHERE status = ? AND user_id = ?");
                statement.setString(1, NEW.toString());
                statement.setLong(2, CurrentUser.getId());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    allOrders.add(Order.mapRow(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return allOrders;
        }
}
