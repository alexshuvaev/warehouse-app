package com.simbirsoft.internship.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class MustBeUniqueException extends RuntimeException {
    public MustBeUniqueException(String message) {
        super(message);
    }
}
