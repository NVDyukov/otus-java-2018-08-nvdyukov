package ru.otus.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestClass {
    private Class<?> aClass;
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();
    private final List<Method> testMethods = new ArrayList<>();

    public TestClass(Class<?> aClass) {
        this.aClass = aClass;
        init();
    }

    protected void init() {
        Arrays.asList(aClass.getMethods())
                .parallelStream()
                //.filter(e -> !Modifier.isStatic(e.getModifiers()))
                .forEach(e -> {
                    if (isContainsAnnotation(e, Test.class)) {
                        testMethods.add(e);
                        return;
                    }
                    if (isContainsAnnotation(e, Before.class)) {
                        beforeMethods.add(e);
                        return;
                    }
                    if (isContainsAnnotation(e, After.class)) {
                        afterMethods.add(e);
                        return;
                    }
                });
    }

    private boolean isContainsAnnotation(Method method, Class<? extends Annotation> annotationClass) {
        return Objects.nonNull(method.getAnnotation(annotationClass));
    }

    public List<Method> getBeforeMethods() {
        return beforeMethods;
    }

    public List<Method> getAfterMethods() {
        return afterMethods;
    }

    public List<Method> getTestMethods() {
        return testMethods;
    }

    public Class<?> getaClass() {
        return aClass;
    }
}
