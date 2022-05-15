package command.impl;

import command.Command;
import controller.IngredientController;
import view.dialog.IngredientDialog;

public class AddIngredientCommand implements Command {
    private final IngredientController ingredientController;

    public AddIngredientCommand(IngredientController ingredientController) {
        this.ingredientController = ingredientController;

    }

    @Override
    public boolean execute() {
       return ingredientController.addIngredient();
    }
}
