package service;

import model.entity.Reservation;
import model.entity.Table;
import model.entity.User;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll() ;
    Reservation findById(Long id);
    boolean insertReservation(Reservation entity);
    boolean updateReservation(Reservation entity);

    Reservation  deleteById(Long id);

    List<Table> findAllTablesReservedByCurrentUser(Long userId);

    Reservation findByUserAndTableAndStatus(Long tableId);

    List<Reservation> findAllNewReservations();
}
