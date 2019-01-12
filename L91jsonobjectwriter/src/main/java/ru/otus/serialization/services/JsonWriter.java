package ru.otus.serialization.services;

import ru.otus.serialization.types.JsonArray;
import ru.otus.serialization.types.JsonNull;
import ru.otus.serialization.types.JsonObject;
import ru.otus.serialization.types.JsonPrimitive;

public class JsonWriter implements Service {
    private StringBuilder stringBuilder = new StringBuilder();
    private String quotation = "\"";
    private String separator = ",";

    private void frame(Object value, String frameValue) {
        stringBuilder
                .append(frameValue)
                .append(value)
                .append(frameValue);
    }

    @Override
    public void visit(JsonPrimitive jsonPrimitive) {
        Object value = jsonPrimitive.getValue();
        if (value instanceof String || value instanceof Character) {
            frame(value, quotation);
        } else {
            stringBuilder.append(value);
        }
    }

    @Override
    public void visit(JsonNull jsonNull) {
        stringBuilder.append("null");
    }

    @Override
    public void visit(JsonObject jsonObject) {
        stringBuilder.append("{");
        jsonObject.entrySet().forEach(node -> {
            frame(node.getKey(), quotation);
            stringBuilder.append(":");
            node.getValue().accept(this);
            stringBuilder.append(separator);
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("}");
    }

    @Override
    public void visit(JsonArray jsonArray) {
        stringBuilder.append("[");
        jsonArray.forEach(e -> {
            e.accept(this);
            stringBuilder.append(separator);
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
