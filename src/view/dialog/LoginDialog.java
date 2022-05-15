package view.dialog;

import model.binding.UserLoginBindingModel;

import java.util.Scanner;

public class LoginDialog implements EntityDialog<UserLoginBindingModel> {
    public static Scanner scanner = new Scanner(System.in);
    @Override
    public UserLoginBindingModel input() {
        var loginBindingModel = new UserLoginBindingModel();
        while (loginBindingModel.getUsername() == null){
            System.out.println("LOGIN: ");
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            if(username.length() < 2 || username.length() >15){
                System.out.println("Invalid username.Your username must be between 2 and 15 characters");
            }else{
                loginBindingModel.setUsername(username);
            }
        }
        while (loginBindingModel.getPassword() == null){
            System.out.println("Enter your password");
            String password = scanner.nextLine();
            if(password.length() != 4){
                System.out.println("Your password must be exactly 4 characters");
            }else{
                loginBindingModel.setPassword(password);
            }
        }
        return loginBindingModel;
    }
}
