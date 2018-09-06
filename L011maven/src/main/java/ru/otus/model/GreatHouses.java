package ru.otus.model;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class GreatHouses {
    @SerializedName("GreatHouses")
    private final List<House> houseList = new LinkedList<>();

    public List<House> getHouseList() {
        return houseList;
    }

    public Boolean add(House house) {
        return this.houseList.add(house);
    }

    public void clear() {
        this.houseList.clear();
    }

    public Person searchForPersonByName(String name) throws Exception {
        for (House e : houseList) {
            Person p = e.searchForPersonByName(name);
            if (Objects.nonNull(p)) {
                return p;
            }
        }
        throw new Exception("Не удалось найти Person c именем " + name);
    }
}
