package model.entity;

import formating.Center;
import model.enums.PaymentTypeEnum;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class Payment  extends BaseEntity implements Serializable {
private User user;
private Order order;
private Table table;
private final String code ="BG5467830567";
private PaymentTypeEnum type ;
private double price;

    public Payment() {
    }

    public static Payment mapRow(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();
        payment.setId(resultSet.getLong("id"));
        payment.setType(PaymentTypeEnum.valueOf(resultSet.getString("type")));
        payment.setPrice(resultSet.getDouble("price"));
        payment.setCreated(resultSet.getObject("created", LocalDateTime.class));
        payment.setModified(resultSet.getObject("modified",LocalDateTime.class));
        return payment;
    }

    public User getUser() {
        return user;
    }

    public Payment setUser(User user) {
        this.user = user;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public Payment setOrder(Order order) {
        this.order = order;
        return this;
    }

    public Table getTable() {
        return table;
    }

    public Payment setTable(Table table) {
        this.table = table;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PaymentTypeEnum getType() {
        return type;
    }

    public Payment setType(PaymentTypeEnum type) {
        this.type = type;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Payment setPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
//        return new StringJoiner(" | ", "", "")
//                .add(super.toString())
//                .add("Server name= "+user.getFirstName())
//                .add("order id= "+ order.getId())
//                .add("table number= "+ table.getCode())
//                .add("restaurant code=" + code)
//                .add("payment type= "+type.toString())
//                .add("amount = "+price)
//                .add("created= " + super.getCreated())
//                .add("modified= " + super.getModified())
//                .toString();
        return String.format("|%s|%n|%s|%n|%s|%n|%s|%n|%s|%n|%s|%n|%s|",
                Center.centerString(30,"Server name= "+user.getFirstName()),
                Center.centerString(30,"order id= "+ order.getId()),
                Center.centerString(30,"table number= "+ table.getCode()),
                Center.centerString(30,"restaurant code=" + code),
                Center.centerString(30,"total amount = "+price),
                Center.centerString(30,"payment type= "+type.toString()),
                Center.centerString(30,"date= " + super.getCreated().format(DateTimeFormatter.ofPattern("yyyy.M.dd HH:mm"))));


    }
}
