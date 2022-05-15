package repository.impl;

import exceptions.NonExistingEntityException;
import model.entity.*;
import repository.*;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 class PaymentRepositoryImpl implements PaymentRepository {
    private final Connection connection;
    private final UserRepository userRepository;
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;


    public PaymentRepositoryImpl(Connection connection, UserRepository userRepository, TableRepository tableRepository, OrderRepository orderRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment findById(Long id) throws NonExistingEntityException {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM payment WHERE id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                throw new NonExistingEntityException("There is no payment with id = "+id);
            }
            Payment payment = Payment.mapRow(resultSet);
            User user = userRepository.findById(resultSet.getLong("user_id"));
            Order order = orderRepository.findById(resultSet.getLong("order_id"));
            Table table = tableRepository.findById(resultSet.getLong("table_id"));
            payment.setUser(user);
            payment.setOrder(order);
            payment.setTable(table);

            return payment;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean insert(Payment entity) {
        String insertSQL = "INSERT INTO payment VALUES (?,?,?,?,?,?,?,?,?)";
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

    private int setRowWithoutId(Payment entity, PreparedStatement statement, int i) throws SQLException {
        statement.setLong(i++,entity.getUser().getId());
        statement.setLong(i++,entity.getOrder().getId());
        statement.setLong(i++,entity.getTable().getId());
        statement.setString(i++,entity.getCode());
        statement.setString(i++,entity.getType().toString());
        statement.setDouble(i++,entity.getPrice());
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getCreated()));
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getModified()));
        return i;
    }

    @Override
    public boolean update(Payment entity) throws NonExistingEntityException {
        if (entity.getId() == null || findById(entity.getId()) == null){
            throw  new NonExistingEntityException("There is no such payment  ,persisted in database");
        }
        String updateSQL = "UPDATE payment " +
                "SET user_id = ?, order_id = ? , table_id = ? , code = ? , type = ? , " +
                "price = ? ,created = ?,modified = ? " +
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
    public Payment deleteById(Long id) throws NonExistingEntityException {
        var deleted = findById(id);
        String deleteSQL =  "DELETE FROM payment " +
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
    public List<Payment> findAll()  {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM payment");
            ResultSet resultSet = statement.executeQuery();
            List<Payment> payments = new ArrayList<>();
            while (resultSet.next()){
                Payment payment = Payment.mapRow(resultSet);
                payment.setUser(userRepository.findById(resultSet.getLong("user_id")));
                payment.setOrder(orderRepository.findById(resultSet.getLong("order_id")));
                payment.setTable(tableRepository.findById(resultSet.getLong("table_id")));
                payments.add(payment);
            }
            return payments;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public long count(String table) throws SQLException {
        return findAll().size();
    }

     @Override
     public void printRecipe(Payment payment) {
         System.out.println(payment);

     }
 }
