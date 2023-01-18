package com.uni42.verticalapi.exception;

public class DuplicateCustomerException extends Exception {

    public DuplicateCustomerException(String username) {
        super("Customer with username=" + username + ", already exists.");
    }
}
