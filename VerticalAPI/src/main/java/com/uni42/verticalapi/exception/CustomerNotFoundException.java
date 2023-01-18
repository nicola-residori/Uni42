package com.uni42.verticalapi.exception;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException(String username) {
        super("Customer with username=" + username + ", not found.");
    }
}
