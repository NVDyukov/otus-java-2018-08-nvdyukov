package ru.otus.exceptions;

public class ORMException extends RuntimeException {
    public ORMException(String message) {
        super(message);
    }

    public ORMException(Throwable cause) {
        super(cause);
    }
}
