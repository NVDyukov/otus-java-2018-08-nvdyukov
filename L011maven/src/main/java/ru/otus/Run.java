package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.model.GreatHouses;
import ru.otus.model.Person;
import ru.otus.serialize.PersonSerializer;
import ru.otus.utils.HouseMaker;

public class Run {
    public static void main(String[] args) {
        GreatHouses houses = HouseMaker.createHouses();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Person.class, new PersonSerializer())
                .create();
        String s = gson.toJson(houses);
        System.out.println(s);
        System.out.println();
        try {
            Person person = houses.searchForPersonByName("Дейнерис");
            person.setTitle("Матерь драконов. Кхалиси Великого травяного моря");
            s = gson.toJson(houses);
            System.out.println("Изменили титул у Дейнерис: ");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
