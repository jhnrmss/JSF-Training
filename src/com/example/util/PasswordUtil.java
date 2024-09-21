package com.example.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    // Method to generate a salt
    public  byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    // Method to hash the password with the salt
    public  String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Method to verify the password
    public  boolean verifyPassword(String enteredPassword, String storedPassword, byte[] salt) {
        String hashedEnteredPassword = hashPassword(enteredPassword, salt);
        return hashedEnteredPassword.equals(storedPassword);
    }

 
}