package ru.otus.model;

import java.util.Objects;

public abstract class DataSet {
    private long id;

    public DataSet() {
    }

    public DataSet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
