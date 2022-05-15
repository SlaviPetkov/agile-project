package view.dialog;

import model.entity.Item;
import model.enums.IngredientTypeEnum;
import model.enums.ItemTypeEnum;
import security.CurrentUser;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.enums.ItemTypeEnum.*;

public class ItemDialog implements EntityDialog<Item> {
    private final Scanner scanner = new Scanner(System.in);
    Pattern SPECIAL_CHARACTERS_PATTERN  = Pattern.compile("\\w+");
    @Override
    public Item input() {
        Item item = new Item();
        item.setUser(CurrentUser.getUser());
        while(item.getTitle()== null){
            System.out.println("Enter item title:");
            String title = scanner.nextLine().trim();
            Matcher matcher = SPECIAL_CHARACTERS_PATTERN.matcher(title);
            if(title.length() < 2 || title.length() > 40 || !matcher.matches() ){
                System.out.println("Invalid title.Title must be without special characters and between 2 and 25 characters long");
            }else{
                item.setTitle(title);
            }
        }
        while(item.getSlug()== null){
            System.out.println("Enter item slug:");
            String slug = scanner.nextLine().trim();
            Matcher matcher = SPECIAL_CHARACTERS_PATTERN.matcher(slug);
            if( slug.length() > 25 || !matcher.matches() ){
                System.out.println("Invalid slug.Slug must be without special characters and max 25 characters long");
            }else{
                item.setSlug(slug);
            }
        }
        while(item.getSummary()== null){
            System.out.println("Enter item summary:");
            String summary = scanner.nextLine().trim();
            if( summary.length() < 5 || summary.length() > 150  ){
                System.out.println("Invalid summary.Summary must be without special characters and between 5 and 150 characters long");
            }else{
                item.setSummary(summary);
            }
        }
        while(item.getType() == null){
            System.out.println("Enter item type:");
            System.out.print("(");
            for ( ItemTypeEnum type : values()){
                System.out.printf("%s / ",type.toString());
            }
            System.out.print(")");
            System.out.println();
            String type = scanner.nextLine().trim().toUpperCase(Locale.ROOT);

            if( Arrays.stream(values()).noneMatch(ing->ing.toString().equals(type))){
                System.out.println("Invalid type.Choose correct type.");
            }else{
                item.setType(valueOf(type));
            }
        }
        if( !item.getType().equals(DRINK)){
            item.setCooking(true);
        }

            while (item.getPrice() == 0.0) {
                System.out.println("Enter price:");
                double price;
                try {
                    price = Double.parseDouble(scanner.nextLine().trim());
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid price.Enter valid number.");
                    continue;
                }
                item.setPrice(price);
                item.setCurrency("lv");
                item.setRecipe("");
                item.setInstructions("");
                item.setContent("");

            }
        if(item.isCooking()) {
            while (item.getRecipe() == null) {
                System.out.println("Enter item recipe:");
                String recipe = scanner.nextLine().trim();
                Matcher matcher = SPECIAL_CHARACTERS_PATTERN.matcher(recipe);
                if (recipe.length() < 5 || recipe.length() > 1500 || matcher.matches()) {
                    System.out.println("Invalid recipe.Recipe must be without special characters and between 5 and 1500 characters long");
                } else {
                    item.setRecipe(recipe);
                }
            }
            while (item.getInstructions() == null) {
                System.out.println("Enter item instructions:");
                String instructions = scanner.nextLine().trim();
                Matcher matcher = SPECIAL_CHARACTERS_PATTERN.matcher(instructions);
                if (instructions.length() < 5 || instructions.length() > 1500 || matcher.matches()) {
                    System.out.println("Invalid instructions.Instructions must be without special characters and between 5 and 1500 characters long");
                } else {
                    item.setInstructions(instructions);
                }
            }
        }

        return item;
    }


}
