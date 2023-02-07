package com.hookahShop.HookahShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User Not Found")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String email) {
        super("User with email: " + email + " not found.");
    }

}
