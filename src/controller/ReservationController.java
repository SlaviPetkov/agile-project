package controller;

import model.entity.Reservation;
import model.entity.Table;
import service.ReservationService;
import service.TableService;
import view.dialog.ReservationDialog;
import view.dialog.TableDialog;

import java.util.List;

public class ReservationController {
    private final ReservationService reservationService;
    private final TableService tableService;

    public ReservationController(ReservationService reservationService, TableService tableService) {
        this.reservationService = reservationService;
        this.tableService = tableService;
    }
    public boolean makeReservation(){
        List<Table> allFreeTables = tableService.findAllFreeTables();
        if (allFreeTables.isEmpty()) {
            System.out.println("All tables are reserved");
            return false;
        } else {
            TableDialog.showFreeTables(allFreeTables);
            Table table =TableDialog.input(tableService);
            Reservation reservation = new ReservationDialog().input();
            reservation.setTable(table);
            reservationService.insertReservation(reservation);
            table.setReservation(reservationService.findByUserAndTableAndStatus(table.getId()));
            tableService.updateTableToReserved(table);

            return true;
        }
    }
public boolean showReservations(){
    reservationService.findAllNewReservations().stream().forEach(reservation -> {
        System.out.printf("|%-20s|%-35s|%-30s|%-435s|%n",
                "table code = "+reservation.getTable().getCode(),
        "customer name = "+reservation.getFullName(),
        "mobile = "+reservation.getMobile(),
        "date and time = " + reservation.getDateTimeOfReservation().toString());
    });
    return true;
}

}
