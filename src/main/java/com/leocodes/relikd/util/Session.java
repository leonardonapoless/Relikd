package com.leocodes.relikd.util;

import com.leocodes.relikd.model.Role;
import com.leocodes.relikd.model.User;

public class Session {

    private static Session instance;
    private User currentUser;

    private Session() {
    }

    public static Session get() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void login(User user) {
        this.currentUser = user;
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean isAdmin() {
        return isLoggedIn() && currentUser.role() == Role.ADMIN;
    }
}
