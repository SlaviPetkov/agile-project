package command.impl;

import command.Command;
import security.CurrentUser;

public class LogoutCommand implements Command {
    @Override
    public boolean execute() {
        CurrentUser.setId(CurrentUser.DEFAULT_ID);
        CurrentUser.setUsername(CurrentUser.ANONYMOUS);
        CurrentUser.setIsServer(false);
        CurrentUser.setIsManager(false);
        CurrentUser.setIsAdmin(false);

        return false;
    }
}
