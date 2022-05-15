package model.entity;

import model.enums.ItemTypeEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.StringJoiner;

public class Item extends BaseEntity{
    private User user ;
    private String title;
    private String slug;
    private String summary;
    private ItemTypeEnum type;
    private boolean cooking;
    private double price;
    private String currency;
    private String recipe;
    private String instructions;
    private String content;

    public Item() {
    }

    public static Item mapRow(ResultSet resultSet) throws SQLException {
        Item item  = new Item();
        item.setId(resultSet.getLong("id"));
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        item.setUser(user)
        .setTitle(resultSet.getString("title"))
        .setTitle(resultSet.getString("title"))
                .setSlug(resultSet.getString("slug"))
                .setSummary(resultSet.getString("summary"))
                .setType(ItemTypeEnum.valueOf(resultSet.getString("type").toUpperCase(Locale.ROOT)))
                .setCooking(resultSet.getBoolean("cooking"))
                .setPrice(resultSet.getDouble("price"))
                .setCurrency(resultSet.getString("currency"))
                .setRecipe(resultSet.getString("recipe"))
                .setInstructions(resultSet.getString("instructions"))
                .setContent(resultSet.getString("content"))
                .setCreated(resultSet.getObject("created", LocalDateTime.class))
                .setModified(resultSet.getObject("modified",LocalDateTime.class));


        return item;
    }

    public User getUser() {
        return user;
    }

    public Item setUser(User user) {
        this.user = user;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Item setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public Item setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public Item setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public ItemTypeEnum getType() {
        return type;
    }

    public Item setType(ItemTypeEnum type) {
        this.type = type;
        return this;
    }

    public boolean isCooking() {
        return cooking;
    }

    public Item setCooking(boolean cooking) {
        this.cooking = cooking;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Item setPrice(double price) {
        this.price = price;
        return this;
    }
    public String getCurrency() {
        return currency;
    }

    public Item setCurrency(String currency) {
        this.currency =currency;
        return this;
    }

    public String getRecipe() {
        return recipe;
    }

    public Item setRecipe(String recipe) {
        this.recipe = recipe;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public Item setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Item setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(" | ", "", "")
                .add(super.toString())
                .add("title= "+title)
                .add("slug= "+slug)
                .add("summary= "+summary)
                .add("type= "+type)
                .add("cooking= "+cooking)
                .add("price= "+price)
                .add("recipe= "+recipe)
                .add("instructions= "+instructions)
                .add("content= "+content)
                .add("created= " + super.getCreated())
                .add("modified= " + super.getModified())
                .toString();
    }
}
