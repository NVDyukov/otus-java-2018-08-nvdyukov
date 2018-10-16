package ru.otus.test;

import ru.otus.core.After;
import ru.otus.core.Before;
import ru.otus.core.Test;

public class TestClass {

    @Test
    public void testMethod1() {
        System.out.println("testMethod1");
    }

    @Test
    public void testMethod2() {
        System.out.println("testMethod2");
    }

    @Before
    public void beforeMethod1() {
        System.out.println("beforeMethod1");
    }

    @After
    public void afterMethod1() {
        System.out.println("afterMethod1");
    }

    @Test
    public void testMethod3() {
        System.out.println("testMethod3");
    }
}
