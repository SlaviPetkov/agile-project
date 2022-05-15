package command.impl;

import command.Command;
import controller.UserController;
import model.entity.User;
import view.dialog.RegisterDialog;

public class RegisterUserCommand implements Command {
 private final UserController userController;

    public RegisterUserCommand( UserController userController) {
        this.userController = userController;

    }

    @Override
    public boolean execute() {
        return userController.register();
    }
}
