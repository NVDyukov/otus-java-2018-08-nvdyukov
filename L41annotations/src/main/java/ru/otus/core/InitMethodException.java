package ru.otus.core;

import java.util.List;

public class InitMethodException extends Exception {
    private List<Throwable> errors;

    public InitMethodException(List<Throwable> errors) {
        this.errors = errors;
    }

    public List<Throwable> getCauses() {
        return this.errors;
    }
}
