package ru.otus.exceptions;

public class DBServiceException extends RuntimeException {
    public DBServiceException(Throwable cause) {
        super(cause);
    }
}
