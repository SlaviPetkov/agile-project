package view.dialog;

import model.entity.Ingredient;
import model.entity.User;
import model.enums.IngredientTypeEnum;
import model.enums.UnitMeasureEnum;
import security.CurrentUser;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IngredientDialog implements EntityDialog<Ingredient> {
    Pattern SPECIAL_CHARACTERS_PATTERN  = Pattern.compile("\\w+");
    Scanner scanner = new Scanner(System.in);
    @Override
    public Ingredient input() {
        Ingredient ingredient = new Ingredient();
        ingredient.setUser(CurrentUser.getUser());
        while(ingredient.getTitle()== null){
            System.out.println("Enter ingredient title:");
            String title = scanner.nextLine().trim();
            Matcher matcher = SPECIAL_CHARACTERS_PATTERN.matcher(title);
            if(title.length() < 2 || title.length() > 25 || !matcher.matches() ){
                System.out.println("Invalid title.Title must be without special characters and between 2 and 25 characters long");
            }else{
                ingredient.setTitle(title);
            }
        }
        while(ingredient.getSlug()== null){
            System.out.println("Enter ingredient slug:");
            String slug = scanner.nextLine().trim();
            Matcher matcher = SPECIAL_CHARACTERS_PATTERN.matcher(slug);
            if( slug.length() > 25 || !matcher.matches() ){
                System.out.println("Invalid slug.Slug must be without special characters and max 25 characters long");
            }else{
                ingredient.setSlug(slug);
            }
        }
        while(ingredient.getType() == null){
            System.out.println("Enter ingredient type:");
            System.out.print("(");
            for ( IngredientTypeEnum type : IngredientTypeEnum.values()){
                System.out.printf("%s / ",type.toString());
            }
            System.out.print(")");
            System.out.println();
            String type = scanner.nextLine().trim().toUpperCase(Locale.ROOT);

            if( Arrays.stream(IngredientTypeEnum.values()).noneMatch(ing->ing.toString().equals(type))){
                System.out.println("Invalid type.Choose correct type.");
            }else{
                ingredient.setType(IngredientTypeEnum.valueOf(type));
            }
        }  while(ingredient.getQuantity() == null){
            System.out.println("Enter quantity:");
            double quantity ;
            try {
                 quantity = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid quantity.Enter valid number.");
                continue;
            }
            ingredient.setQuantity(quantity);

        }
        while(ingredient.getUnit() == null){
            System.out.println("Enter unit measure:");
            System.out.print("(");
            for (UnitMeasureEnum unit:UnitMeasureEnum.values()){
                System.out.printf("%s / ",unit);
            }
            System.out.print(")");
            System.out.println();
            String unit = scanner.nextLine();
            if(Arrays.stream(UnitMeasureEnum.values()).noneMatch(un->un.equals(UnitMeasureEnum.valueOf(unit)))){
                System.out.println("Invalid unit.Enter valid unit");
            }else {
                ingredient.setUnit(UnitMeasureEnum.valueOf(unit));
            }

        }
        while(ingredient.getContent() == null){
            System.out.println("Enter content:");
            String content = scanner.nextLine();
            if(content.equals("")){
                ingredient.setContent("");
            }else {
                if (content.length() < 5 || content.length() > 150) {
                    System.out.println("Invalid content .Content must be between 5 and 150 characters");
                } else {
                    ingredient.setContent(content);
                }
            }
    }
        return ingredient;
}
}
