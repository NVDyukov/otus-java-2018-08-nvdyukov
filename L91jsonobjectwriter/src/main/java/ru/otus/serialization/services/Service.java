package ru.otus.serialization.services;

import ru.otus.serialization.types.JsonArray;
import ru.otus.serialization.types.JsonNull;
import ru.otus.serialization.types.JsonObject;
import ru.otus.serialization.types.JsonPrimitive;

public interface Service {
    void visit(JsonPrimitive jsonPrimitive);

    void visit(JsonNull jsonNull);

    void visit(JsonObject jsonObject);

    void visit(JsonArray jsonArray);
}
