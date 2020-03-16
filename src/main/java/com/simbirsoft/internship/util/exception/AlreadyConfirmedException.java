package com.simbirsoft.internship.util.exception;

public class AlreadyConfirmedException extends RuntimeException {
    public AlreadyConfirmedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
