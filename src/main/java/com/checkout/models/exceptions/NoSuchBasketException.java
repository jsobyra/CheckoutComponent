package com.checkout.models.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoSuchBasketException extends Exception {

    public NoSuchBasketException(String message) {
        super(message);
    }
}
