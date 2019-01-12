package ru.otus.serialization.types;

import ru.otus.serialization.services.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JsonObject extends JsonElement {
    private Object value;

    private final LinkedHashMap<String, JsonElement> nodes =
            new LinkedHashMap<String, JsonElement>();

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return nodes.entrySet();
    }

    public void add(String property, JsonElement value) {
        if (value == null) {
            value = JsonNull.INSTANCE;
        }
        nodes.put(property, value);
    }

    @Override
    public void accept(Service service) {
        service.visit(this);
    }
}
