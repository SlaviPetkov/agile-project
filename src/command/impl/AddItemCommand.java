package command.impl;

import command.Command;
import controller.ItemController;

public class AddItemCommand implements Command {
   private final ItemController itemController;

    public AddItemCommand(ItemController itemController) {
        this.itemController = itemController;
    }

    @Override
    public boolean execute() {

        return itemController.addItem();
    }
}
