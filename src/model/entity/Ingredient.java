package model.entity;

import model.enums.IngredientTypeEnum;
import model.enums.UnitMeasureEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.StringJoiner;

public class Ingredient extends BaseEntity{
    private User user;
    private String title;
    private String slug;
    private IngredientTypeEnum type;
    private Double quantity;
    private UnitMeasureEnum unit;
    private String content;

    public Ingredient() {
    }

    public static Ingredient mapRow(ResultSet resultSet) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(resultSet.getLong("id"));
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        ingredient.setUser(user)
        .setTitle(resultSet.getString("title"))
        .setSlug(resultSet.getString("slug"))
        .setType(IngredientTypeEnum.valueOf(resultSet.getString("type").toUpperCase(Locale.ROOT)))
        .setQuantity(resultSet.getDouble("quantity"))
        .setUnit(UnitMeasureEnum.valueOf(resultSet.getString("unit")))
        .setContent(resultSet.getString("content"))
        .setCreated(resultSet.getObject("created", LocalDateTime.class))
        .setModified(resultSet.getObject("modified",LocalDateTime.class));
        return ingredient;

    }

    public User getUser() {
        return user;
    }

    public Ingredient setUser(User user) {
        this.user = user;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Ingredient setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public Ingredient setSlug(String slug) {
        this.slug = slug;
        return this;
    }


    public IngredientTypeEnum getType() {
        return type;
    }

    public Ingredient setType(IngredientTypeEnum type) {
        this.type = type;
        return this;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Ingredient setQuantity(double quantity) {
        this.quantity = quantity;
        return this;
    }

    public UnitMeasureEnum getUnit() {
        return unit;
    }

    public Ingredient setUnit(UnitMeasureEnum unit) {
        this.unit = unit;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Ingredient setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String
    toString() {
        return new StringJoiner(" | ", "", "")
                .add(super.toString())
                .add("title= "+title)
                .add("slug= "+slug)
                .add("type= "+type)
                .add("quantity= "+quantity)
                .add("unit= "+unit)
                .add("content= "+content)
                .add("created= " + super.getCreated())
                .add("modified= " + super.getModified())
                .toString();
    }
}
