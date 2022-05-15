package command.impl;

import command.Command;
import controller.ReservationController;

public class ShowReservationCommand implements Command {
    private final ReservationController reservationController;

    public ShowReservationCommand(ReservationController reservationController) {
        this.reservationController = reservationController;
    }

    @Override
    public boolean execute() {
     return reservationController.showReservations();

    }
}
