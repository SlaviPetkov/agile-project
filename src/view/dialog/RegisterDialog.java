package view.dialog;

import model.entity.User;
import model.enums.GenderEnum;
import model.enums.RoleTypeEnum;
import security.CurrentUser;

import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterDialog implements EntityDialog<User> {
    public static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-z]{2,6}$");
    public static final  Pattern PASSWORD_PATTERN =
            Pattern.compile("\\d{4}");
    public static final  Pattern MOBILE_PATTERN =
            Pattern.compile("08\\d{8}");
    public static final Pattern NAME_PATTERN =
            Pattern.compile("[a-zA-Z]");
    Scanner scanner = new Scanner(System.in);

    @Override
    public User input() {
        User user = new User();
        while (user.getFirstName() == null){
            System.out.println("Enter first name:");
            String firstName = scanner.nextLine().trim();
            Matcher matcher = NAME_PATTERN.matcher(firstName);
            if(firstName.length() < 2 || firstName.length() >15 || matcher.matches()){
                System.out.println("Invalid name. Your first name must be between 2 and 15 characters");
            }else{
                firstName = firstName.substring(0,1).toUpperCase(Locale.ROOT) + firstName.substring(1).toLowerCase(Locale.ROOT);
                user.setFirstName(firstName);
            }
        }
        while (user.getLastName() == null){
            System.out.println("Enter last name:");
            String lastName = scanner.nextLine().trim();
            Matcher matcher = NAME_PATTERN.matcher(lastName);
            if(lastName.length() < 2 || lastName.length() >15 || matcher.matches()){
                System.out.println("Invalid name. Your last name must be between 2 and 15 characters");
            }else{
                lastName = lastName.substring(0,1).toUpperCase(Locale.ROOT) + lastName.substring(1).toLowerCase(Locale.ROOT);
                user.setLastName(lastName);
            }
        }
        while (user.getUsername() == null){
            System.out.println("Enter username:");
            String username = scanner.nextLine().trim();
            if(username.length() < 2 || username.length() >15){
                System.out.println("Invalid username. Your username must be between 2 and 15 characters");
            }else{
                user.setUsername(username);
            }
        }
        while (user.getPassword() == null){
            System.out.println("Enter password:");
            String password = scanner.nextLine().trim();
            Matcher passwordMatcher = PASSWORD_PATTERN.matcher(password);
            if(!passwordMatcher.matches()){
                System.out.println("Invalid password. Your password must be 4 digits");
            }else{
                user.setPassword(password);
            }
        }
        while (user.getMobile() == null){
            System.out.println("Enter mobile:");
            String mobile = scanner.nextLine().trim();
            Matcher mobileMatcher = MOBILE_PATTERN.matcher(mobile);
            if(!mobileMatcher.matches()){
                System.out.println("Invalid mobile. Mobile number must be 10 digits, starting with \"08\"");
            }else{
                user.setMobile(mobile);
            }
        }
        while (user.getEmail() == null){
            System.out.println("Enter email:");
            String email = scanner.nextLine().trim();
            Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
            if(!emailMatcher.matches()){
                System.out.println("Invalid email.");
            }else{
                user.setEmail(email);
            }
        }
        while (user.getGender() == null){
            System.out.println("Enter gender:  MALE / FEMALE");
            String gender = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
            if(!gender.equals("MALE") && !gender.equals("FEMALE") ){
                System.out.println("Invalid gender.");
            }else{
                user.setGender(GenderEnum.valueOf(gender));
            }
        }
        while (user.getRole() == null) {
            if (CurrentUser.isAdmin()) {
                System.out.print("Roles: (");
                for (RoleTypeEnum role:RoleTypeEnum.values()) {
                    System.out.printf("%s /",role.toString());

                }
                System.out.printf(")%n");
                String role = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
                if (!role.equals("ADMINISTRATOR") && !role.equals("MANAGER") && !role.equals("SERVER")) {
                    System.out.println("Invalid role.");
                } else {
                    user.setRole(RoleTypeEnum.valueOf(role));
                }
            } else {
                user.setRole(RoleTypeEnum.SERVER);
            }
        }

        return user;
    }
}
