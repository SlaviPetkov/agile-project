package model.entity;

import model.enums.OrderStatusEnum;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.StringJoiner;

public class Order extends BaseEntity implements Serializable {
    private User user;
    private Table table;
    private double price;
    private OrderStatusEnum status;

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public Order setUser(User user) {
        this.user = user;

        return this;
    }

    public Table getTable() {
        return table;
    }

    public Order setTable(Table table) {
        this.table = table;

        return this;
    }

    public double getPrice() {
        return price;
    }

    public Order setPrice(double price) {
        this.price = price;

        return this;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public Order setStatus(OrderStatusEnum status) {
        this.status = status;

        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(" | ", "", "")
                .add(super.toString())
                .add("server name ="+user.getFirstName())
                .add("table number ="+table.getCode())

                .add("price ="+price)
                .add("status ="+status.toString())
                .add("created= " + super.getCreated())
                .add("modified= " + super.getModified())
                .toString();
    }

    public static Order mapRow(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        Table table = new Table();
        table.setId(resultSet.getLong("table_id"));

        order.setUser(user);
        order.setTable(table);

        order.setPrice(resultSet.getDouble("price"));
        order.setStatus(OrderStatusEnum.valueOf(resultSet.getString("status")));
        order.setCreated(resultSet.getObject("created", LocalDateTime.class));
        order.setModified(resultSet.getObject("modified",LocalDateTime.class));
        return order;
    }
}
