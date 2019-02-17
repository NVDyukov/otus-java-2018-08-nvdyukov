package ru.otus.dao;

import ru.otus.model.DataSet;

import java.io.Serializable;
import java.util.List;

public interface Dao<T extends DataSet, K extends Serializable> extends AutoCloseable {
    public void persist(T entity);

    public void update(T entity);

    public T findById(K id);

    public void delete(T entity);

    public List<T> findAll();

    public void deleteAll();
}
