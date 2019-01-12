package ru.otus.serialization;

import ru.otus.serialization.types.JsonElement;
import ru.otus.serialization.types.JsonPrimitive;

import java.util.Arrays;
import java.util.Collection;

public class Person {
    private String test = "test";
    private String name;
    private int age;
    private Object object = null;
    private int[] array = {15, 25};
    private Collection<Object> collection = Arrays.asList("Test1", 5, "Test2", null);
    private JsonElement father = new JsonPrimitive(18);

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public JsonElement getFather() {
        return father;
    }
}
