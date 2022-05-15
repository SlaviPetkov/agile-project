package controller;

import command.impl.AddItemCommand;
import command.impl.*;
import model.entity.User;
import security.CurrentUser;
import service.*;
import view.Menu.Menu;
import view.Menu.Option;
import view.dialog.LoginDialog;
import view.dialog.RegisterDialog;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static model.enums.RoleTypeEnum.*;

public class UserController {
    private final UserService userService;
    private final IngredientService ingredientService;
    private final ItemService itemService;
    private final TableService tableService;
    private final ReservationService reservationService;
    private final OrderService orderService;

    private final Scanner scanner = new Scanner(System.in);
    private User currentUser;

    public UserController(UserService userService, IngredientService ingredientService, ItemService itemService, TableService tableService, ReservationService reservationService, OrderService orderService) {
        this.userService = userService;

        this.ingredientService = ingredientService;
        this.itemService = itemService;
        this.tableService = tableService;
        this.reservationService = reservationService;
        this.orderService = orderService;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public UserController setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public boolean login() {

        var loginBindingModel = new LoginDialog().input();
        User user = userService.findByUsernameAndPassword(loginBindingModel.getUsername()
                , loginBindingModel.getPassword());
        if (user == null) {
            System.out.println("Incorrect username or password");
             return  login();
        }

        CurrentUser.setUser(user);
        CurrentUser.setId(user.getId());
        CurrentUser.setUsername(user.getUsername());
        if (user.getRole().equals(ADMINISTRATOR)) {
            CurrentUser.setIsAdmin(true);
        } else if (user.getRole().equals(MANAGER)) {
            CurrentUser.setIsManager(true);
        }else{
            CurrentUser.setIsServer(true);
        }
        return true;
    }
    public boolean register(){
        User user = new RegisterDialog().input();
        return userService.insertUser(user);
    }
}
