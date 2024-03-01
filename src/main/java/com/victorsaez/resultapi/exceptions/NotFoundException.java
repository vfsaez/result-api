package com.victorsaez.resultapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
        super("Resource not found with Id: " + id);
    }

    public NotFoundException(String Resource, Long id) {
        super(String.format("%s not found with Id: %s", Resource, id));
    }
}
