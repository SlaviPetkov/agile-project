package command.impl;

import command.Command;
import controller.UserController;
import model.entity.User;
import security.CurrentUser;
import view.dialog.LoginDialog;

import static model.enums.RoleTypeEnum.ADMINISTRATOR;
import static model.enums.RoleTypeEnum.MANAGER;

public class LoginCommand implements Command {
    private final UserController userController;

    public LoginCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean execute() {
        return userController.login();
    }
}
