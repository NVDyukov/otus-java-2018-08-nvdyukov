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
    public void run(Class clazz) throws InitMethodException, InvalidTestException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Objects.requireNonNull(clazz);
        validateClass(clazz);
        validatePublicConstructorNoArg(clazz);
        TestClass testClass = new TestClass(clazz);
        validateTestClassMethods(testClass);
        Object o = clazz.getConstructor().newInstance();
        runTestMethods(o, testClass);
    }

    private void validateTestClassMethods(TestClass testClass) throws InitMethodException {
        List<Throwable> errors = new ArrayList<>();
        validateMethods(testClass.getBeforeMethods(), errors);
        validateMethods(testClass.getTestMethods(), errors);
        validateMethods(testClass.getAfterMethods(), errors);
        if (!errors.isEmpty()) {
            throw new InitMethodException(errors);
        }
    }

    private void validatePublicConstructorNoArg(Class clazz) throws InvalidTestException {
        Constructor[] constructors = clazz.getConstructors();
        boolean isValid = constructors.length == 1
                && Modifier.isPublic(constructors[0].getModifiers()) && constructors[0].getParameters().length == 0;
        if (!isValid) {
            throw new InvalidTestException(clazz.getName()
                    + " : у тестового класса должен быть один публичный конструктор без параметров");
        }
    }

    private void validateContainsTestMethod(TestClass testClass) throws InvalidTestException {
        List<Method> testMethods = testClass.getTestMethods();
        if (testMethods.isEmpty()) {
            throw new InvalidTestException(testClass.getaClass().getName()
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

    private void validateClass(Class<?> clazz) throws InvalidTestException {
        int modifiers = clazz.getModifiers();
        boolean b = Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers) || Modifier.isStatic(modifiers);
        if (b) {
            throw new InvalidTestException(clazz.getName()
                    + " класс не должен быть: abstract, interface, static");
        }
    }

    private void runMethods(Object object, List<Method> methodList) {
        methodList.stream()
                .forEach(e -> {
                    try {
                        e.invoke(object);
                    } catch (ReflectiveOperationException e1) {
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
                    } catch (ReflectiveOperationException e1) {
                        e1.printStackTrace();
                    }
                    runMethods(object, testClass.getAfterMethods());
                });
    }
}
