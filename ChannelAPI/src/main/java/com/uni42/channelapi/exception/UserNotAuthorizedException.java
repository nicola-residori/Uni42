package com.uni42.channelapi.exception;

public class UserNotAuthorizedException extends Exception {

    public UserNotAuthorizedException() {
        super("User is not authorized for this functionality or resource");
    }
}
