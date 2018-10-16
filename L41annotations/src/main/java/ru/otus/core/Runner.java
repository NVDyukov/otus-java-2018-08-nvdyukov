package ru.otus.core;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public final class Runner {
    private Runner() {
    }

    public static void Run(Class clazz) {
        TestRunner testRunner = new TestRunner();
        try {
            testRunner.Run(clazz);
        } catch (InitMethodError initMethodError) {
            initMethodError.getCauses()
                    .stream()
                    .forEach(e -> e.printStackTrace());
        }
    }

    public static void Run(String sPackage) {
        try {
            ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
            classPath.getTopLevelClassesRecursive(sPackage)
                    .stream()
                    .map(e -> e.load())
                    .filter(e -> validateContainsTestMethod(e))
                    .forEach(e -> Run(e));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validateContainsTestMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .anyMatch(e -> Objects.nonNull(e.getAnnotation(Test.class)));
    }
}
