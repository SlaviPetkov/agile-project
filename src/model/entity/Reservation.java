package model.entity;

import model.enums.ReservationStatusEnum;
import service.ReservationService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class Reservation extends BaseEntity {
    DateTimeFormatter DATE_TIME_FORMATTER =DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm");
    private Table table ;
    private User user;
    private String fullName;
    private String mobile;
    private LocalDateTime dateTimeOfReservation;
    private String content;
    private ReservationStatusEnum status;

    public Reservation() {

    }

    public static Reservation mapRow(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(resultSet.getLong("id"));
        Table table =new Table();
        table.setId(resultSet.getLong("table_id"));
        reservation.setTable(table);
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        reservation.setUser(user);
        reservation.setFullName(resultSet.getString("full_name"));
        reservation.setMobile(resultSet.getString("mobile"));
        reservation.setDateTimeOfReservation(resultSet.getObject("date_of_reservation",LocalDateTime.class));
        table.setCreated(resultSet.getObject("created", LocalDateTime.class));
        table.setModified(resultSet.getObject("modified",LocalDateTime.class));
        reservation.setContent(resultSet.getString("content"));
        reservation.setStatus(ReservationStatusEnum.valueOf(resultSet.getString("status")));
        return reservation;
    }

    public Table getTable() {
        return table;
    }

    public Reservation setTable(Table table) {
        this.table = table;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Reservation setUser(User user) {
        this.user = user;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Reservation setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public Reservation setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public LocalDateTime getDateTimeOfReservation() {
        return dateTimeOfReservation;
    }

    public Reservation setDateTimeOfReservation(LocalDateTime dateTimeOfReservation) {
        this.dateTimeOfReservation = dateTimeOfReservation;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Reservation setContent(String content) {
        this.content = content;
        return this;
    }

    public ReservationStatusEnum getStatus() {
        return status;
    }

    public Reservation setStatus(ReservationStatusEnum status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(" | ", "", "")
                .add(super.toString())
                .add("table code = "+table.getCode())
                .add("customer name = "+fullName)
                .add("mobile = "+mobile)
                .add("date and time= "+dateTimeOfReservation.format(DATE_TIME_FORMATTER))
                .add("content= " + content)
                .add("created= " + super.getCreated())
                .add("modified= " + super.getModified())

                .toString();
    }
}
