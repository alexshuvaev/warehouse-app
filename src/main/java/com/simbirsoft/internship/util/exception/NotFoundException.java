package com.simbirsoft.internship.util.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}