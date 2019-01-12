package ru.otus.serialization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyJsonTest {
    private MyJson myJson;

    @BeforeAll
    public void init() {
        myJson = new MyJson();
    }

    @Test
    public void toJsonNullTest() {
        String expected = "null";
        Assertions.assertEquals(expected, myJson.toJson((Object) null));
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
        String expected = "{\"test\":\"test\",\"name\":\"test\",\"age\":110,\"object\":null,\"array\":[15,25]," +
                "\"collection\":{\"a\":[\"Test1\",5,\"Test2\",null]},\"father\":{\"value\":18}}";
        String actual = myJson.toJson(new Person("test", 110));
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void toJsonArrayTest() {
        String expected = "[\"test\",555,[5,88]]";
        String actual = myJson.toJson(new Object[]{"test", 555, new int[]{5, 88}});
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void toJsonCollectionTest() {
        String expected = "{\"a\":[\"Test1\",5875,\"Test2\"]}";
        String actual = myJson.toJson(Arrays.asList("Test1", 5875, "Test2"));
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }
}
