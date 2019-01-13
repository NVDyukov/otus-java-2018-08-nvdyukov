package ru.otus.serialization;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyJsonTest {
    private MyJson myJson;
    private Gson gson;

    @BeforeAll
    public void init() {
        myJson = new MyJson();
        gson = new Gson();
    }

    @Test
    public void toJsonNullTest() {
        String expected = gson.toJson(null);
        Assertions.assertEquals(expected, myJson.toJson(null));
    }

    @Test
    public void toJsonPrimitivesTest() {
        String doubleQuotes = "\"";
        String str = "toJsonPrimitivesTest";
        Assertions.assertEquals(doubleQuotes + str + doubleQuotes, myJson.toJson(str));
        int i = 5;
        Assertions.assertEquals(String.valueOf(i), myJson.toJson(i));
        char c = 'T';
        Assertions.assertEquals(doubleQuotes + c + doubleQuotes, myJson.toJson(c));
    }

    @Test
    public void toJsonObjectTest() {
        Person person = new Person("test", 110);
        String expected = gson.toJson(person);
        String actual = myJson.toJson(person);
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void toJsonArrayTest() {
        Object[] objects = {"test", 555, new int[]{5, 88}};
        String expected = gson.toJson(objects);
        String actual = myJson.toJson(objects);
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void toJsonCollectionTest() {
        List<?> list = Arrays.asList("Test1", 5875, "Test2");
        String expected = gson.toJson(list);
        String actual = myJson.toJson(list);
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void toJsonComplexTest() {
        Assertions.assertEquals("null", myJson.toJson(null));
        String expected = myJson.toJson(new int[]{1, 2, 3});
        Assertions.assertEquals(expected, myJson.toJson(List.of(1, 2, 3)));
        Assertions.assertEquals(expected, myJson.toJson(Arrays.asList(1, 2, 3)));
    }
}
