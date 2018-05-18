package com.knoldus;

public class LoginUtil {
    public static boolean authenticate(String userEnteredPassword, String dbPassword) {
        return userEnteredPassword.equals(dbPassword);
    }
}
