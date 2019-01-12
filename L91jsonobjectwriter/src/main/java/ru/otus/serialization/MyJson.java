package ru.otus.serialization;

import ru.otus.serialization.services.JsonWriter;
import ru.otus.serialization.types.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MyJson {

    private static boolean isNotStaticAndNotTransient(Field e) {
        int modifiers = e.getModifiers();
        return !Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers);
    }

    private JsonElement getJsonArray(Object src) {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < Array.getLength(src); i++) {
            Object o = Array.get(src, i);
            if (o == null) {
                jsonArray.add(null);
                continue;
            }
            Class<?> aClass = o.getClass();
            if (JsonElement.isConvertToJsonPrimitive(aClass)) {
                jsonArray.add(new JsonPrimitive(o));
            } else if (JsonElement.isConvertToJsonArray(aClass)) {
                jsonArray.add(getJsonArray(o));
            } else {
                jsonArray.add(getJsonObject(o, aClass));
            }

        }
        return jsonArray;
    }

    private JsonElement getJsonObject(Object src, Class<?> clazz) {
        JsonObject jsonObject = new JsonObject();
        Object objectField;
        List<Field> fieldList = getFilterFields(clazz, MyJson::isNotStaticAndNotTransient);
        for (Field e : fieldList) {
            e.setAccessible(true);
            try {
                objectField = e.get(src);
                String name = e.getName();
                if (objectField == null) {
                    jsonObject.add(name, null);
                    continue;
                }
                Class<?> oClass = objectField.getClass();
                if (JsonElement.isConvertToJsonPrimitive(oClass)) {
                    e.setAccessible(true);
                    jsonObject.add(name, new JsonPrimitive(objectField));
                } else if (JsonElement.isConvertToJsonArray(oClass)) {
                    jsonObject.add(name, getJsonArray(objectField));
                } else {
                    jsonObject.add(name, getJsonObject(objectField, oClass));
                }
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return jsonObject;
    }

    private List<Field> getFilterFields(Class<?> clazz, Predicate<Field> filter) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(filter)
                .collect(Collectors.toList());
    }

    protected String toJson(JsonElement jsonElement) {
        JsonWriter jsonWriter = new JsonWriter();
        jsonElement.accept(jsonWriter);
        return jsonWriter.toString();
    }

    protected String toJson(Object src, Class<?> clazz) {
        if (JsonElement.isConvertToJsonPrimitive(clazz)) {
            return toJson(new JsonPrimitive(src));
        } else if (JsonElement.isConvertToJsonArray(clazz)) {
            return toJson(getJsonArray(src));
        } else {
            return toJson(getJsonObject(src, clazz));
        }
    }

    public String toJson(Object src) {
        return src == null
                ? toJson(JsonNull.INSTANCE)
                : toJson(src, src.getClass());
    }

}
