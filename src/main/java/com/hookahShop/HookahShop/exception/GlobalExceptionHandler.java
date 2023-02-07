package com.hookahShop.HookahShop.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = UserNotFoundException.class)
    public String userNotFoundException(final Model model) {
        logger.info("UserNotFoundException");
        model.addAttribute("error", "Bad request.");
        return "login";
    }

    @ExceptionHandler(value = NullPointerException.class)
    public String nullPointerException() {
        logger.info("NullPointerException");
        return "redirect:/";
    }

    @ExceptionHandler(value = HookahNotFoundException.class)
    public String hookahNotFoundException() {
        logger.info("HookahNotFoundException");
        return "redirect:/hookahs";
    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    public String orderNotFoundException() {
        logger.info("OrderNotFoundException");
        return "redirect:/orders";
    }


}
