package service.impl;

import exceptions.NonExistingEntityException;
import model.entity.Ingredient;
import repository.IngredientRepository;
import repository.UserRepository;

import service.IngredientService;

import java.sql.SQLException;
import java.util.List;

class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepo;
    private final UserRepository userRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepo, UserRepository userRepository) {
        this.ingredientRepo = ingredientRepo;
        this.userRepository = userRepository;
    }

    @Override
    public List<Ingredient> findAll() {
        try {
            List<Ingredient> all = ingredientRepo.findAll();
            for (Ingredient ingredient : all) {
                ingredient.setUser(userRepository.findById(ingredient.getUser().getId()));
            }
            return all;
        } catch ( NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ingredient findById(Long id) {
        try {
            Ingredient ingredient = ingredientRepo.findById(id);
            ingredient.setUser(userRepository.findById(ingredient.getUser().getId()));
            return ingredient;
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertIngredient(Ingredient ingredient) {
        return ingredientRepo.insert(ingredient);
    }

    @Override
    public boolean updateIngredient(Ingredient ingredient) {
        try {
            return ingredientRepo.update(ingredient);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Ingredient deleteById(Long id) {
        try {
            return ingredientRepo.deleteById(id);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
