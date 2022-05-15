package controller;

import formating.Center;
import model.entity.*;
import model.enums.OrderStatusEnum;
import model.enums.PaymentTypeEnum;
import model.enums.TableStatusEnum;
import security.CurrentUser;
import service.*;
import view.dialog.OrderDialog;
import view.dialog.OrderItemDialog;
import view.dialog.PaymentDialog;
import view.dialog.TableDialog;
import view.input.EnterPaymentMethod;
import view.input.EnterTableCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static model.enums.TableStatusEnum.*;

public class OrderController {
    private final TableService tableService;
    private final OrderService orderService;
    private final ReservationService reservationService;
    private final ItemService itemService;
    private final ItemController itemController;
    private final Scanner scanner = new Scanner(System.in);
    private final OrderItemService orderItemService;
    private final PaymentService paymentService;

    public OrderController(TableService tableService, OrderService orderService,
                           ReservationService reservationService, ItemService itemService,
                           ItemController itemController, OrderItemService orderItemService, PaymentService paymentService) {
        this.tableService = tableService;
        this.orderService = orderService;
        this.reservationService = reservationService;
        this.itemService = itemService;
        this.itemController = itemController;
        this.orderItemService = orderItemService;

        this.paymentService = paymentService;
    }
    public boolean makeOrder(){
        List<Long> allItemIds = itemService.findAllItemIds();
        TableDialog.showAllTables(tableService);
        System.out.println("Enter table code: ");
        Table table = new EnterTableCode(tableService).input();
        if(table.getStatus().equals(RESERVED)){
            if(table.getReservation().getDateTimeOfReservation().isAfter(LocalDateTime.now())){
                System.out.printf("This table is reserved for %s%n",table.getReservation().getDateTimeOfReservation());

                return makeOrder();
            }
        }
        if(table.getStatus().equals(ACTIVE)){
            String choice = new OrderDialog(table).showOrder(orderItemService);

            if(choice.equalsIgnoreCase("YES")){
                PaymentTypeEnum paymentType = new EnterPaymentMethod().input();
                Payment payment = new PaymentDialog(table,paymentType).input();
                paymentService.insertPayment(payment);
                paymentService.printRecipe(payment);
                orderService.updateOrder(table.getOrder().setStatus(OrderStatusEnum.FINISHED));
                tableService.updateTableToFree(table);

                return true;
            }



        }


            String choice;
            do {
                itemController.showMenu();
                System.out.printf("%-49s|%50s%n", "TO CONTINUE TYPE | 1 |",
                        "BACK TO MENU TYPE | 2 |");
                choice = makeChoiceToBackToContinue();

                if (!choice.equals("2")) {

                    if (table.getStatus().equals(FREE) || table.getStatus().equals(RESERVED)) {

                        Order order = new OrderDialog(table).input();
                        orderService.insertOrder(order);
                        order = orderService.findOrderByUserAndTable(table.getId());
                        table.setOrder(order);
                        table.setStatus(ACTIVE);
                        tableService.updateTableToActive(table);

                    }

                    OrderItem orderItem = new OrderItemDialog().input(allItemIds);

                    orderItem.setItem(itemService.findById(orderItem.getItem().getId()));
                    orderItem.setPrice(orderItem.getItem().getPrice() * orderItem.getQuantity());
                    orderItem.setOrder(table.getOrder());
                    orderItemService.insertOrderItem(orderItem);
                    table.getOrder().setPrice(table.getOrder().getPrice() + orderItem.getItem().getPrice() * orderItem.getQuantity());
                    table.getOrder().setModified(LocalDateTime.now());
                    orderService.updateOrder(table.getOrder());

                }
            } while (!choice.equalsIgnoreCase("2"));




        return true;

    }

    private String makeChoiceToBackToContinue() {
        String choice;
        choice = scanner.nextLine().trim();
        if(!choice.equals("1") && !choice.equals("2")){
            System.out.println(Center.centerString(100,"Invalid action.Enter 1 or 2 "));
            return makeChoiceToBackToContinue();
        }
        return choice;
    }


}
