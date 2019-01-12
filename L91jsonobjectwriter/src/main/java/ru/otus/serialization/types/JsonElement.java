package ru.otus.serialization.types;

import ru.otus.serialization.services.Service;

public abstract class JsonElement {
    public abstract void accept(Service service);

    public static boolean isConvertToJsonPrimitive(Class<?> clazz) {
        return JsonPrimitive.typeValidation(clazz);
    }

    public static boolean isConvertToJsonObject(Class<?> clazz) {
        return !isConvertToJsonPrimitive(clazz) && !isConvertToJsonArray(clazz);
    }

    public static boolean isConvertToJsonArray(Class<?> clazz) {
        return clazz.isArray();
    }
}
