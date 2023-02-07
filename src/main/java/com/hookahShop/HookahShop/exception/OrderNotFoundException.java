package com.hookahShop.HookahShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order Not Found")
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(final long id) {
        super("Order with id: " + id + " not found.");
    }
}
