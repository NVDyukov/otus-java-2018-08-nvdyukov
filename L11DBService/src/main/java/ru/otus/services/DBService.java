package ru.otus.services;

import ru.otus.model.DataSet;

public interface DBService extends AutoCloseable {
    public <T extends DataSet> void save(T user);

    public <T extends DataSet> T load(long id, Class<T> clazz);
}
