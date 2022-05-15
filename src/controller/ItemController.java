package controller;

import formating.Center;
import model.entity.Item;
import model.enums.ItemTypeEnum;
import service.ItemService;
import view.dialog.ItemDialog;

import java.util.List;
import java.util.stream.Collectors;

public class ItemController {
    private final ItemService itemService;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    public boolean showItems(){
        List<Item> allItems = itemService.findAll();
        for (ItemTypeEnum itemTypeEnum : ItemTypeEnum.values()) {
            System.out.println(itemTypeEnum);
            allItems.stream().filter(item -> item.getType().equals(itemTypeEnum)).forEach(System.out::println);
        }
        return true;
    }
    public boolean addItem(){
        return itemService.insertItem(new ItemDialog().input());
    }
    public boolean showMenu(){
        List<Item> allItems = itemService.findAll();
        int i = 1;
        for (ItemTypeEnum itemTypeEnum : ItemTypeEnum.values()) {
            System.out.println(Center.centerString(101,itemTypeEnum.toString()+"S"));
            System.out.println("-".repeat(100));
            List<Item> items = allItems.stream().
                    filter(item -> item.getType().equals(itemTypeEnum)).collect(Collectors.toList());
            for (Item item : items) {
                System.out.printf("%-49s|%49s|%n",""+(i++)+"."+item.getTitle(),"|code = "+item.getId());

            }
            System.out.println("-".repeat(100));

        }
        return true;
    }

}
