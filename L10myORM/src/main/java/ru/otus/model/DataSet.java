package ru.otus.model;

import ru.otus.annotations.Id;

import java.util.Objects;

public abstract class DataSet {
    @Id
    private long id;

    public DataSet() {
    }

    public DataSet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSet)) return false;
        DataSet dataSet = (DataSet) o;
        return id == dataSet.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "id=" + id +
                '}';
    }
}
