package ru.otus.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestRunner {
    public void Run(Class clazz) throws InitMethodError {
        Objects.requireNonNull(clazz);
        validateClass(clazz);
        validatePublicConstructorNoArg(clazz);
        TestClass testClass = new TestClass(clazz);
        validateContainsTestMethod(testClass);
        validateTestClassMethods(testClass);
        try {
            Object o = clazz.getConstructor().newInstance();
            runTestMethods(o, testClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void validateTestClassMethods(TestClass testClass) throws InitMethodError {
        List<Throwable> errors = new ArrayList<>();
        validateMethods(testClass.getBeforeMethods(), errors);
        validateMethods(testClass.getTestMethods(), errors);
        validateMethods(testClass.getAfterMethods(), errors);
        if (!errors.isEmpty()) {
            throw new InitMethodError(errors);
        }
    }

    private void validatePublicConstructorNoArg(Class clazz) {
        Constructor[] constructors = clazz.getConstructors();
        boolean isValid = constructors.length == 1
                && Modifier.isPublic(constructors[0].getModifiers()) && constructors[0].getParameters().length == 0;
        if (!isValid) {
            throw new RuntimeException(clazz.getName()
                    + " : у тестового класса должен быть один публичный конструктор без параметров");
        }
    }

    private void validateContainsTestMethod(TestClass testClass) {
        List<Method> testMethods = testClass.getTestMethods();
        if (testMethods.isEmpty()) {
            throw new RuntimeException(testClass.getaClass().getName()
                    + " не содержит методов, помеченных аннотацией @Test");
        }
    }

    private boolean validateContainsTestMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .anyMatch(e -> Objects.nonNull(e.getAnnotation(Test.class)));
    }

    private void validateMethods(List<Method> methodList, List<Throwable> errors) {
        methodList
                .parallelStream()
                .forEach((Method e) -> {
                    if (!Modifier.isPublic(e.getModifiers()) || Modifier.isStatic(e.getModifiers())) {
                        errors.add(new Exception(e.getName() + "() метод должен быть не статическим и публичным"));
                        return;
                    }
                    if (e.getParameterCount() > 0) {
                        errors.add(new Exception(e.getName() + "() метод не должен содержать параметры"));
                        return;
                    }
                    if (e.getReturnType() != Void.TYPE) {
                        errors.add(new Exception(e.getName() + "() метод должен возвращать только void"));
                        return;
                    }
                });
    }

    private void validateClass(Class<?> clazz) {
        int modifiers = clazz.getModifiers();
        boolean b = Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers) || Modifier.isStatic(modifiers);
        if (b) {
            throw new RuntimeException(clazz.getName()
                    + " класс не должен быть: abstract, interface, static");
        }
    }

    private void runMethods(Object object, List<Method> methodList) {
        methodList.stream()
                .forEach(e -> {
                    try {
                        e.invoke(object);
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (InvocationTargetException e1) {
                        e1.printStackTrace();
                    } catch (Throwable e1) {
                        e1.printStackTrace();
                    }
                });
    }

    private void runTestMethods(Object object, TestClass testClass) {
        testClass.getTestMethods().stream()
                .forEach(e -> {
                    runMethods(object, testClass.getBeforeMethods());
                    System.out.println("ТЕСТ: " + e.getName());
                    try {
                        e.invoke(object);
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (InvocationTargetException e1) {
                        e1.printStackTrace();
                    } catch (Throwable e1) {
                        e1.printStackTrace();
                    }
                    runMethods(object, testClass.getAfterMethods());
                });
    }
}
