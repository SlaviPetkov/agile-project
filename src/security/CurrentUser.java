package security;

import model.entity.User;
import model.enums.RoleTypeEnum;

public class CurrentUser {
    public static final Long DEFAULT_ID = 0L;
    private static Long id = DEFAULT_ID;
    public static final String ANONYMOUS = "ANONYMOUS";
    private static String username = ANONYMOUS;
    private static boolean isAdmin = false;
    private static boolean isManager = false;
    private static boolean isServer = false;
    private static User user;

    public static Long getId() {
        return id;
    }

    public static void setId(Long id) {
        CurrentUser.id = id;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CurrentUser.username = username;
    }

    public static boolean isAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        CurrentUser.isAdmin = isAdmin;
    }

    public static boolean isManager() {
        return isManager;
    }

    public static void setIsManager(boolean isManager) {
        CurrentUser.isManager = isManager;
    }

    public static boolean isServer() {
        return isServer;
    }

    public static void setIsServer(boolean isServer) {
        CurrentUser.isServer = isServer;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        CurrentUser.user = user;
    }
}
