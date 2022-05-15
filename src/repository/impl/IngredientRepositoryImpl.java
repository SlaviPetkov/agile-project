package repository.impl;

import exceptions.NonExistingEntityException;
import model.entity.Ingredient;
import repository.IngredientRepository;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class IngredientRepositoryImpl implements IngredientRepository {
    private final Connection connection;

    public IngredientRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Ingredient findById(Long id) throws NonExistingEntityException {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("Select * FROM ingredients WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NonExistingEntityException("There is no ingredient with id = " + id);

            }
            return Ingredient.mapRow(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Ingredient entity) {
        String insertSql = "INSERT INTO ingredients VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertSql);
            int i = 1;
            statement.setLong(i++, 0L);
            setIngredientRowsWithoutId(entity, statement, i);
            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Ingredient entity) throws NonExistingEntityException {
        if (entity.getId() == null || findById(entity.getId()) == null) {
            throw new NonExistingEntityException("There is no such ingredient ,persisted in database");
        }
        String updateSQL = "UPDATE ingredients " +
                "SET  user_id= ?, title = ? , slug = ? , " +
                "type = ? , quantity = ? , unit = ? , " +
                "created = ? , modified = ? , content = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateSQL);
            int i = 1;
            i = setIngredientRowsWithoutId(entity, statement, i);
            statement.setLong(i, entity.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private int setIngredientRowsWithoutId(Ingredient entity, PreparedStatement statement, int i) throws SQLException {
        statement.setLong(i++, entity.getUser().getId());
        statement.setString(i++, entity.getTitle());
        statement.setString(i++, entity.getSlug());
        statement.setString(i++, entity.getType().toString());
        statement.setDouble(i++, entity.getQuantity());
        statement.setString(i++, entity.getUnit().toString());
        statement.setTimestamp(i++, Timestamp.valueOf(entity.getCreated()));
        statement.setTimestamp(i++, Timestamp.valueOf(entity.getModified()));
        statement.setString(i++, entity.getContent());
        return i;

    }

    @Override
    public Ingredient deleteById(Long id) throws NonExistingEntityException {
        Ingredient deleted = findById(id);
        String deleteSQL = "DELETE FROM ingredients " +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setLong(1, id);
            statement.executeUpdate();
            return deleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> allIngredients = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM ingredients");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allIngredients.add(Ingredient.mapRow(resultSet));
            }
            return allIngredients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public long count(String table) {

        return findAll().size();

    }
}