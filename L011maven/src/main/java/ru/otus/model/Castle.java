package ru.otus.model;

public class Castle {
    private String name;

    public Castle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Castle{" +
                "name='" + name + '\'' +
                '}';
    }
}
