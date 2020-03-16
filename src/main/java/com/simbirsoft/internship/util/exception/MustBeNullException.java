package com.simbirsoft.internship.util.exception;

public class MustBeNullException extends RuntimeException {
    public MustBeNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
