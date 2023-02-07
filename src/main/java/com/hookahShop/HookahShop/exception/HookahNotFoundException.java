package com.hookahShop.HookahShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Hookah Not Found")
public class HookahNotFoundException extends RuntimeException {

    public HookahNotFoundException(final long id) {
        super("Hookah with id: " + id + " not found.");
    }

}
