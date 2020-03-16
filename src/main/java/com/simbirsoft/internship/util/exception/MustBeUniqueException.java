package com.simbirsoft.internship.util.exception;

public class MustBeUniqueException extends RuntimeException {
    public MustBeUniqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
