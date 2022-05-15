package command.impl;

import command.Command;
import controller.ItemController;

public class ShowItemsCommand implements Command {
   private final ItemController itemController;

    public ShowItemsCommand(ItemController itemController) {
        this.itemController = itemController;
    }

    @Override
    public boolean execute() {
       return itemController.showItems();
    }
}
