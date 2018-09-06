package ru.otus.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.otus.model.Person;

import java.lang.reflect.Type;

public class PersonSerializer implements JsonSerializer<Person> {
    @Override
    public JsonElement serialize(Person person, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Имя", person.getName());
        jsonObject.addProperty("Титул", person.getTitle());
        return jsonObject;
    }
}
