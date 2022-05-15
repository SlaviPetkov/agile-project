package model.entity;

import model.enums.TableStatusEnum;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.StringJoiner;

public class Table extends BaseEntity implements Serializable {
    private String code;
    private TableStatusEnum status;
    private int capacity;
    private String content;
    private Reservation reservation;
    private Order order;

    public Table() {
    }

    public static Table mapRow(ResultSet resultSet) throws SQLException {
        Table table = new Table();
        table.setId(resultSet.getLong("id"));
        table.setCode(resultSet.getString("code"));
        table.setStatus(TableStatusEnum.valueOf(resultSet.getString("status")));
        if(table.getStatus().equals(TableStatusEnum.RESERVED)){
            Reservation reservation = new Reservation();
            reservation.setId(resultSet.getLong("reservation_id"));
            table.setReservation(reservation);
        }
        if(table.getStatus().equals(TableStatusEnum.ACTIVE)){
            Order order = new Order();
            order.setId(resultSet.getLong("order_id"));
            table.setOrder(order);
        }
        table.setCapacity(resultSet.getInt("capacity"));
        table.setCreated(resultSet.getObject("created", LocalDateTime.class));
        table.setModified(resultSet.getObject("modified",LocalDateTime.class));
        table.setContent(resultSet.getString("content"));
        return table;
    }

    public String getCode() {
        return code;
    }

    public Table setCode(String code) {
        this.code = code;
        return this;
    }

    public TableStatusEnum getStatus() {
        return status;
    }

    public Table setStatus(TableStatusEnum status) {
        this.status = status;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public Table setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Table setContent(String content) {
        this.content = content;
        return this;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Table setReservation(Reservation reservation) {
        this.reservation = reservation;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public Table setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(" | ", "", "")
                .add(super.toString())
                .add("code= "+code)
                .add("status= "+status.toString())
                .add("capacity= "+capacity)
                .add("content= "+content)
                .add("created= " + super.getCreated())
                .add("modified= " + super.getModified())
                .add("reservation id= " + reservation.getId())
                .toString();
    }
}
