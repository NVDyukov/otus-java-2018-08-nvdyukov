package ru.otus.serialization.types;

import ru.otus.serialization.services.Service;

public final class JsonPrimitive extends JsonElement {
    private static final Class<?>[] ALLOWABLE_TYPES = {int.class, long.class, short.class,
            float.class, double.class, byte.class, boolean.class, char.class, Integer.class, Long.class,
            Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class, String.class};

    private Object value;

    void setValue(Object value) {
        if (!typeValidation(value.getClass()))
            throw new IllegalArgumentException("Недопустимый тип элемента");
        this.value = value;
    }

    static boolean typeValidation(Class<?> clazz) {
        for (Class<?> type : ALLOWABLE_TYPES) {
            if (type.isAssignableFrom(clazz))
                return true;
        }
        return false;
    }

    public Object getValue() {
        return value;
    }

    public JsonPrimitive(Object object) {
        this.setValue(object);
    }

    public JsonPrimitive(Boolean bool) {
        this.setValue(bool);
    }

    public JsonPrimitive(Number number) {
        this.setValue(number);
    }

    public JsonPrimitive(String string) {
        this.setValue(string);
    }

    public JsonPrimitive(Character character) {
        this.setValue(String.valueOf(character));
    }

    @Override
    public void accept(Service service) {
        service.visit(this);
    }
}
