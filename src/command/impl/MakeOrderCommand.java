package command.impl;

import command.Command;
import controller.OrderController;

public class MakeOrderCommand implements Command {


private final OrderController orderController;

    public MakeOrderCommand(OrderController orderController) {
        this.orderController = orderController;
    }

    @Override
    public boolean execute() {
       return orderController.makeOrder();
  }}



