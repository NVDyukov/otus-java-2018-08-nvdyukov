package ru.otus.reflect;

import ru.otus.reflect.types.ValidClassType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ClassesHelper {
    private ClassesHelper() {
    }

    private static <T> String getValue(Object object, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Object o = field.get(object);
        return String.valueOf((ValidClassType.isCharacterOrString(o)) ? "\'" + o + "\'" : o);
    }

    public static List<Class<?>> getSuperClasses(Class<?> clazz) {
        ArrayList<Class<?>> classes = new ArrayList<>();
        do {
            classes.add(clazz);
        } while ((clazz = clazz.getSuperclass()) != Object.class);
        return classes;
    }

    public static Map<String, Field> getClassFields(Class<?> clazz, Predicate<Field> filter) {
        return Arrays.stream(clazz.getDeclaredFields())
                .parallel()
                .filter(filter)
                .collect(Collectors.toMap(Field::getName, v -> v));
    }

    public static Map<String, Field> getClassesFields(Class<?> clazz, Predicate<Field> filter) {
        return getClassesFields(getSuperClasses(clazz), filter);
    }

    public static Map<String, Field> getClassesFields(List<Class<?>> classes, Predicate<Field> filter) {
        Map<String, Field> fields = new HashMap<>();
        classes.forEach(e -> {
            fields.putAll(getClassFields(e, filter));
        });
        return fields;
    }

    public static Map<String, String> getClassFields(Object object, Class<?> clazz, Predicate<Field> filter) {
        return Arrays.stream(clazz.getDeclaredFields())
                .parallel()
                .filter(filter)
                .collect(Collectors.toMap(Field::getName, v -> {
                    try {
                        return getValue(object, v);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return "";
                }));
    }

    public static Map<String, String> getClassesFields(Object object, Class<?> clazz, Predicate<Field> filter) {
        return getClassesFields(object, getSuperClasses(clazz), filter);
    }

    public static Map<String, String> getClassesFields(Object object, List<Class<?>> classes, Predicate<Field> filter) {
        Map<String, String> fields = new HashMap<>();
        classes.forEach(e -> {
            fields.putAll(getClassFields(object, e, filter));
        });
        return fields;
    }

    public static Map<String, String> convertToMapValueFields(Object object, Map<String, Field> classesField) {
        Map<String, String> valueFields = new HashMap<>();
        classesField.forEach((k, v) -> {
            try {
                valueFields.put(k.toUpperCase(), getValue(object, v));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return valueFields;
    }

    public static <T> T initObject(Class<T> clazz, Predicate<Field> filter, Map<String, Object> values)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Map<String, Field> classesFields = getClassesFields(clazz, filter);
        Class.forName(clazz.getName());
        Field field = null;
        T object = clazz.getDeclaredConstructor().newInstance();
        for (Map.Entry<String, Field> entry : classesFields.entrySet()) {
            field = entry.getValue();
            field.setAccessible(true);
            field.set(object, values.get(entry.getKey().toUpperCase()));
        }
        return object;
    }
}
