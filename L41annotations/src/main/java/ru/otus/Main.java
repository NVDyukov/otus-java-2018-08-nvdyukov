package ru.otus;

import ru.otus.core.Runner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Runner.Run("ru.otus.test");
        Thread.sleep(20);
        Runner.Run(TestClass.class);
    }
}
