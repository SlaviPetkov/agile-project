package view.dialog;

import model.entity.Item;
import model.entity.OrderItem;

import java.util.List;
import java.util.Scanner;

public class OrderItemDialog  {
    private final Scanner scanner = new Scanner(System.in);

    public OrderItemDialog() {

    }


    public OrderItem input(List<Long> itemIds) {
        OrderItem orderItem = new OrderItem();
        Item item = new Item();
        while (item.getId() == null) {
            System.out.println("Enter item code:");
            Long itemId = null;
            try {
                itemId = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid item code");
            }
            if (!itemIds.contains(itemId)) {
                System.out.printf("Invalid item code.Type correct item code %s%n", itemIds);
            }else{
                item.setId(itemId);
                orderItem.setItem(item);
            }

        }
        while (orderItem.getQuantity() == 0){
        System.out.println("Enter quantity:");

        try {
            int quantity = Integer.parseInt(scanner.nextLine());
            orderItem.setQuantity(quantity);
        }catch (NumberFormatException e){
            System.out.println("Invalid quantity");
        }
        }

        System.out.println("Place comment:");
        String comment = scanner.nextLine();
        orderItem.setComment(comment);
        return orderItem;

    }
}
