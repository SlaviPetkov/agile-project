package util;

import exceptions.ConstraintViolation;
import exceptions.ConstraintViolationException;
import model.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientValidator implements Validator<Ingredient> {
    List<ConstraintViolation> violations = new ArrayList<>();
    @Override
    public void validate(Ingredient entity) throws ConstraintViolationException {

        var titleLength = entity.getTitle().trim().length();
        if(titleLength  < 2 || titleLength > 25){
            violations.add(new ConstraintViolation(entity.getClass().getName(),
                    "title",entity.getTitle(),"Ingredient title length must be between 2 and 25 characters "));
        }



    }
}
