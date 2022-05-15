package command.impl;

import command.Command;
import controller.IngredientController;

public class ShowInventoryCommand implements Command {
  private final IngredientController ingredientController;

    public ShowInventoryCommand(IngredientController ingredientController) {
        this.ingredientController = ingredientController;

    }

    @Override
    public boolean execute() {
        return ingredientController.showInventory();
    }
}
