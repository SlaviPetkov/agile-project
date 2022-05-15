package controller;

import service.IngredientService;
import view.dialog.IngredientDialog;

public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    public boolean showInventory(){
        System.out.println("-".repeat(200));
        ingredientService.findAll().stream().forEach(i-> {
            System.out.println(i);
            System.out.println("-".repeat(200));
        }
        );

        return true;
    }
    public boolean addIngredient(){
        return  ingredientService.insertIngredient(new IngredientDialog().input());

    }
}
