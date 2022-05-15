package service;

import model.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAll() ;
    Ingredient findById(Long id);
    boolean insertIngredient(Ingredient ingredient);
    boolean updateIngredient(Ingredient ingredient);

    Ingredient  deleteById(Long id);
}
