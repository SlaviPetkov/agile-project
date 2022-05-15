package model.entity;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.StringJoiner;

public class OrderItem extends BaseEntity{
    private Item item ;
    private int quantity;
    private double price;
    private Order order;
    private String comment;

    public OrderItem() {

    }

    public static OrderItem mapRow(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getLong("id"));
        Item item = new Item();
        item.setId(resultSet.getLong("item_id"));
        orderItem.setItem(item);
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setPrice(resultSet.getDouble("price"));
        Order order = new Order();
        order.setId(resultSet.getLong("order_id"));
        orderItem.setOrder(order);
        orderItem.setComment(resultSet.getString("comment"));
        orderItem.setCreated(resultSet.getObject("created", LocalDateTime.class));
        orderItem.setModified(resultSet.getObject("modified",LocalDateTime.class));
        return orderItem;

    }

    public Item getItem() {
        return item;
    }

    public OrderItem setItem(Item item) {
        this.item = item;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public OrderItem setPrice(double price) {
        this.price = price;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public OrderItem setOrder(Order order) {
        this.order = order;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public OrderItem setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(" | ", "", "")
                .add(super.toString())
                .add("item= " + item.getTitle())
                .add("quantity= " +quantity )
                .add("price= " + price)
                .add("order id= "+order.getId())
                .add("comment= "+ comment)
                .add("created= " + super.getCreated())
                .add("modified= " + super.getModified())
                .toString();
    }
}
