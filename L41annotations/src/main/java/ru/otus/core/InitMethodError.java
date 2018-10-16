package ru.otus.core;

import java.util.List;

public class InitMethodError extends Exception {
    private List<Throwable> errors;

    public InitMethodError(List<Throwable> errors) {
        this.errors = errors;
    }

    public List<Throwable> getCauses() {
        return this.errors;
    }
}
