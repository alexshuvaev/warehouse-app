package com.simbirsoft.internship.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidPropertyException extends RuntimeException {
    public InvalidPropertyException(String message) {
        super(message);
    }
}
