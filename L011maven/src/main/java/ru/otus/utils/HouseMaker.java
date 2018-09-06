package ru.otus.utils;

import ru.otus.model.*;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class HouseMaker {

    public static GreatHouses createHouses() {
        var houses = new GreatHouses();
        Person leader;
        House house;
        leader = new Person("Серсея Ланнистер", "Королева андалов и первых людей");
        house = new House("Ланнистеры",
                new Land("Западные земли", "Вестерос"),
                new Castle("Утёс Кастерли"),
                leader);
        house.addAllPerson(Stream.of(new Person("Тайвин Ланнистер", "Лорд"),
                new Person("Джейме Ланнистер", "Сир"), leader)
                .collect(Collectors.toCollection(HashSet::new)));
        houses.add(house);

        leader = new Person("Джон Сноу", "Хранитель Севера");
        house = new House("Старки",
                new Land("Север", "Вестерос"),
                new Castle("Винтерфелл"),
                leader);
        house.addAllPerson(Stream.of(new Person("Санса Старк", "Леди Винтерфелла"),
                new Person("Бран Старк", "Трёхглазый ворон"), leader)
                .collect(Collectors.toCollection(HashSet::new)));
        houses.add(house);

        leader = new Person("Дейнерис", " XXX ");
        house = new House("Таргариены",
                new Land("Миэрин", "Эссос"),
                new Castle("Великая пирамида Миэрина"),
                leader);
        house.addAllPerson(Stream.of(new Person("Эйгон I", "Король Андалов и Первых людей"),
                new Person("Рейгар", "Принц Драконьего Камня"),
                new Person("Эймон", "Мейстер"), leader)
                .collect(Collectors.toCollection(HashSet::new)));
        houses.add(house);

        return houses;
    }
}
