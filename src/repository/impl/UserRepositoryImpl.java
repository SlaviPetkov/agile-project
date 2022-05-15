package repository.impl;

import exceptions.InvalidEntityException;
import exceptions.NonExistingEntityException;
import model.entity.User;
import repository.Repository;
import repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 class UserRepositoryImpl implements UserRepository {
    private final Connection connection;

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;

    }

    @Override
    public User findById(Long id) throws NonExistingEntityException {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                throw new NonExistingEntityException("There is no user with id = "+id);

            }
            return User.mapRow(resultSet);


        } catch (SQLException | InvalidEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(User entity) {
        String insertSql = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(insertSql);
            int i=1;
            statement.setLong(i++,0L);
            statement.setString(i++,entity.getFirstName());
            statement.setString(i++,entity.getLastName());
            statement.setString(i++,entity.getUsername());
            statement.setString(i++,entity.getPassword());
            statement.setString(i++,entity.getMobile());
            statement.setString(i++,entity.getGender().toString());
            statement.setString(i++,entity.getEmail());
            statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getLastLogin()));
            statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getCreated()));

            statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getModified()));
            statement.setString(i,entity.getRole().toString());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(User entity) throws NonExistingEntityException {
        if (entity.getId() == null || findById(entity.getId()) == null){
            throw  new NonExistingEntityException("There is no such user ,persisted in database");
        }
        String updateSQL = "UPDATE users " +
                "SET first_name = ?, last_name = ? , username = ? , " +
                "password = ? , mobile = ? , gender = ? , email = ? , " +
                "last_login = ? , created = ? , modified = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateSQL);
            int i = 1;
            statement.setString(i++,entity.getFirstName());
            statement.setString(i++,entity.getLastName());
            statement.setString(i++,entity.getUsername());
            statement.setString(i++,entity.getPassword());
            statement.setString(i++,entity.getMobile());
            statement.setString(i++,entity.getGender().toString());
            statement.setString(i++,entity.getEmail());
            statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getLastLogin()));
            statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getCreated()));
            statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getModified()));
            statement.setLong(i,entity.getId());
           return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User deleteById(Long id) throws NonExistingEntityException {
        User deletedUser = findById(id);
        String deleteSQL =  "DELETE FROM users " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setLong(1,id);
            statement.executeUpdate();
            return deletedUser;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> allUsers = new ArrayList<>();
        try {
            statement = connection.prepareStatement("Select * from users");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allUsers.add(User.mapRow(resultSet));

            }
            return allUsers;
        } catch (SQLException | InvalidEntityException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public long count(String table) {
        return findAll().size();
    }

     @Override
     public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
         try {
             PreparedStatement statement = connection.prepareStatement(sql);
             statement.setString(1,username);
             statement.setString(2,password);
             ResultSet resultSet = statement.executeQuery();
             if(!resultSet.next()){
                 throw  new InvalidEntityException("Incorrect username or password");
             }
             return User.mapRow(resultSet);
         } catch (SQLException | InvalidEntityException e) {
             e.printStackTrace();
         }
         return null;
     }
 }
