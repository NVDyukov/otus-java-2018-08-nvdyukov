package ru.otus.serialization.types;

import ru.otus.serialization.services.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonArray extends JsonElement implements Iterable<JsonElement> {
    private List<JsonElement> jsonElements = new ArrayList<>();

    @Override
    public void accept(Service service) {
        service.visit(this);
    }

    public void add(JsonElement jsonElement) {
        if (jsonElement == null) {
            jsonElement = JsonNull.INSTANCE;
        }
        jsonElements.add(jsonElement);
    }

    @Override
    public Iterator<JsonElement> iterator() {
        return jsonElements.iterator();
    }
}
