package com.leocodes.relikd.service;

import com.leocodes.relikd.dao.UserDAO;
import com.leocodes.relikd.model.Role;
import com.leocodes.relikd.model.User;
import com.leocodes.relikd.util.PasswordUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public Optional<User> authenticate(String emailOrUsername, String password) throws SQLException {
        var userOpt = userDAO.findByEmail(emailOrUsername);
        if (userOpt.isEmpty()) {
            userOpt = userDAO.findByUsername(emailOrUsername);
        }

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (PasswordUtil.verify(password, user.passwordHash())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public void register(String username, String email, String fullName, String plainTextPassword) throws SQLException {

        if (userDAO.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (userDAO.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        var hashedPassword = PasswordUtil.hash(plainTextPassword);
        boolean isFirstUser = userDAO.countAll() == 0;
        Role role = isFirstUser ? Role.ADMIN : Role.CUSTOMER;
        User newUser = new User(0, username, email, hashedPassword, fullName, role, LocalDateTime.now());

        userDAO.save(newUser);
    }
}
