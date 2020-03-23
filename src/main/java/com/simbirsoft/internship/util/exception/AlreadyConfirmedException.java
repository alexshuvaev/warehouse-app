package com.simbirsoft.internship.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyConfirmedException extends RuntimeException {
    public AlreadyConfirmedException(String message) {
        super(message);
    }
}
