package com.simbirsoft.internship.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class MustBeNullException extends RuntimeException {
    public MustBeNullException(String message) {
        super(message);
    }
}
