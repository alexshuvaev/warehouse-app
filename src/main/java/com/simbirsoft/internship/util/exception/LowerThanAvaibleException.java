package com.simbirsoft.internship.util.exception;

public class LowerThanAvaibleException extends RuntimeException {
    public LowerThanAvaibleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
