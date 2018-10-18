package ru.otus.core;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public final class Runner {
    private Runner() {
    }

    public static void runTestClass(Class clazz) {
        if (!validateContainsTestMethod(clazz)) {
            try {
                throw new InvalidTestException(clazz.getName()
                        + " не содержит методов, помеченных аннотацией @Test");
            } catch (InvalidTestException e) {
                e.printStackTrace();
            }
        }
        run(clazz);
    }

    private static void run(Class clazz) {
        TestRunner testRunner = new TestRunner();
        try {
            testRunner.run(clazz);
        } catch (InitMethodException initMethodException) {
            initMethodException.getCauses()
                    .stream()
                    .forEach(e -> e.printStackTrace());
        } catch (InvalidTestException invalidTestException) {
            invalidTestException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runTestClasses(String sPackage) {
        try {
            ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
            classPath.getTopLevelClassesRecursive(sPackage)
                    .stream()
                    .map(e -> e.load())
                    .filter(e -> validateContainsTestMethod(e))
                    .forEach(e -> run(e));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validateContainsTestMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .anyMatch(e -> Objects.nonNull(e.getAnnotation(Test.class)));
    }
}
