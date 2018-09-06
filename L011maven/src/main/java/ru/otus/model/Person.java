package ru.otus.model;

import java.util.Objects;

public class Person {
    private String name;
    private String title;

    public Person(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(title, person.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, title);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
