package com.victorsaez.resultapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameNotAvailableException extends NotAvailableException {

    public UsernameNotAvailableException() {
        super("Username is already taken");
    }
}
