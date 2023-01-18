package com.uni42.authenticator.exception;

public class DuplicateUserException extends Exception {

    public DuplicateUserException(String username) {
        super("User with username=" + username + ", already exists.");
    }
}
