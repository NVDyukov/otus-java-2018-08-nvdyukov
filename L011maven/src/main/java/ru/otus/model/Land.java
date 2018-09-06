package ru.otus.model;

public class Land {
    private String name;
    private String location;

    public Land(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Land{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
