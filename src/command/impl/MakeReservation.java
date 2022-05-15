package command.impl;

import command.Command;
import controller.ReservationController;

public class MakeReservation implements Command {
  private final ReservationController reservationController;

    public MakeReservation(ReservationController reservationController) {
        this.reservationController = reservationController;
    }

    @Override
    public boolean execute() {
        return reservationController.makeReservation();
    }
}
