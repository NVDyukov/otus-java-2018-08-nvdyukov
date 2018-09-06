package ru.otus.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class House {
    private String name;
    private Land land;
    private Castle castle;
    private Person leader;
    private final Set<Person> persons = new HashSet<>();

    public House() {

    }

    public House(String name, Land land, Castle castle, Person leader) {
        this.name = name;
        this.land = land;
        this.castle = castle;
        this.leader = leader;
    }

    public Person searchForPersonByName(String name) {
        for (Person e : persons) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public Boolean addPerson(Person person) {
        return this.persons.add(person);
    }

    public Boolean addAllPerson(Collection<Person> persons) {
        return this.persons.addAll(persons);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public Person getLeader() {
        return leader;
    }

    public void setLeader(Person leader) {
        this.leader = leader;
    }

    public void clear() {
        this.persons.clear();
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", land=" + land +
                ", castle=" + castle +
                ", leader=" + leader +
                ", persons=" + persons +
                '}';
    }
}
