package repository.impl;

import exceptions.NonExistingEntityException;
import model.entity.Item;
import repository.ItemRepository;
import repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ItemRepositoryImpl implements ItemRepository {
    private final Connection connection;

    public ItemRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Item findById(Long id) throws NonExistingEntityException {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM items WHERE id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                throw new NonExistingEntityException("There is no user with id = "+id);
            }
            return Item.mapRow(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean insert(Item entity)  {
        String insertSql = "INSERT INTO items VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insertSql);
            int i=1;
            statement.setLong(i++,0L);
            statement.setLong(i++,entity.getUser().getId());
            setRowsWithoutId(entity, statement, i);
            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private int setRowsWithoutId(Item entity, PreparedStatement statement, int i) throws SQLException {
        statement.setString(i++, entity.getTitle());
        statement.setString(i++, entity.getSlug());
        statement.setString(i++, entity.getSummary());
        statement.setString(i++, entity.getType().toString());
        statement.setBoolean(i++, entity.isCooking());
        statement.setDouble(i++, entity.getPrice());
        statement.setString(i++, entity.getCurrency());
        statement.setString(i++, entity.getRecipe());
        statement.setString(i++, entity.getInstructions());
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getCreated()));
        statement.setTimestamp(i++,java.sql.Timestamp.valueOf(entity.getModified()));
        statement.setString(i++, entity.getContent());
        return i;
    }

    @Override
    public boolean update(Item entity) throws NonExistingEntityException {
        if (entity.getId() == null || findById(entity.getId()) == null){
            throw  new NonExistingEntityException("There is no such user ,persisted in database");
        }
        String updateSQL = "UPDATE items " +
                "SET title = ?, slug = ? , summary = ? , " +
                "type = ? , cooking = ? , price = ? , currency = ? , " +
                "recipe = ? , instructions = ?, created = ? , modified = ?, " +
                "content = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateSQL);
            int i =1 ;
            i = setRowsWithoutId(entity,statement,i);
            statement.setLong(i,entity.getId());
            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Item deleteById(Long id) throws NonExistingEntityException {
        var deleted = findById(id);
        String deleteSQL =  "DELETE FROM items " +
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
    public List<Item> findAll()  {
        List<Item> allItems = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("Select * FROM items");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                allItems.add(Item.mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return allItems;
    }

    @Override
    public long count(String table) {
        return findAll().size();
    }

    @Override
    public List<Long> findAllItemIds() {
        List<Long> allIds = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("Select id FROM items ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                allIds.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allIds;
    }
}
