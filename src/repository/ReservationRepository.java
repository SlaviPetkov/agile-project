package repository;



import model.entity.Reservation;
import model.entity.Table;
import model.entity.User;

import java.util.List;

public interface ReservationRepository extends Repository<Long, Reservation> {


    List<Long> findAllTablesReservedByCurrentUser(Long userId);

    Reservation findByUserAndTableAndStatus(Long tableId);

    void updateToFinished(Reservation reservation);

    List<Reservation> findAllNewReservations();
}
