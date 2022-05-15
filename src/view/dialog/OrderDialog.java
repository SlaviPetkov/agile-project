package view.dialog;

import formating.Center;
import model.entity.Item;
import model.entity.Order;
import model.entity.OrderItem;
import model.entity.Table;
import model.enums.OrderStatusEnum;
import security.CurrentUser;
import service.OrderItemService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OrderDialog implements EntityDialog<Order> {
    private final Table table ;
    private final Scanner scanner = new Scanner(System.in);

    public OrderDialog(Table table) {
        this.table = table;
    }

    @Override
    public Order input() {
        Order order = new Order();
        order.setUser(CurrentUser.getUser());
        order.setTable(table);
        order.setStatus(OrderStatusEnum.NEW);
        order.setPrice(0.0);
        return order;
    }
    public  String showOrder(OrderItemService orderItemService){
        List<OrderItem> allOrderItems = orderItemService.findAllByOrderId(table.getOrder().getId());
        Map<Item,Integer> items = new LinkedHashMap<>();
        for (OrderItem orderItem : allOrderItems) {
            items.put(orderItem.getItem(), orderItem.getQuantity());

        }
        System.out.println();
        System.out.println(Center.centerString(100,"Table orders:"));
        System.out.println("-".repeat(100));
        items.entrySet().stream().forEach(e-> {
            System.out.printf("%-49s|%49s",e.getKey().getTitle(),"quantity: "+e.getValue());
            System.out.println();
        });
        System.out.println("-".repeat(100));
        System.out.printf("%-49s|%46.2f lv%n","Total amount",
                table.getOrder().getPrice());
        System.out.println("-".repeat(100));
        System.out.printf("%100s%n","FINISH ORDER |YES / NO|");
        String choice = makeChoiceYesNo();
        return choice;
    }

    private String makeChoiceYesNo() {
        String choice = scanner.nextLine();
        if (!choice.equalsIgnoreCase("YES") && !choice.equalsIgnoreCase("NO")) {
            System.out.println("Invalid action.Type :   YES / NO");
            return makeChoiceYesNo();
        }
        return choice;
    }
}
