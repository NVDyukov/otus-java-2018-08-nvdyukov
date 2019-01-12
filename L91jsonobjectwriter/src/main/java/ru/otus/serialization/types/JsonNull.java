package ru.otus.serialization.types;

import ru.otus.serialization.services.Service;

public final class JsonNull extends JsonElement {
    public static final JsonNull INSTANCE = new JsonNull();

    @Override
    public void accept(Service service) {
        service.visit(this);
    }
}
