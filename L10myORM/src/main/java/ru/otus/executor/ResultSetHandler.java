package ru.otus.executor;

import java.sql.ResultSet;

@FunctionalInterface
public interface ResultSetHandler {
    void process(ResultSet resultSet);
}
