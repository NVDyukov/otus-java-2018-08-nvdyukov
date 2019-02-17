package ru.otus.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class UserDataSet extends DataSet {
    private String name;
    private int age;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AddressDataSet address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Set<PhoneDataSet> phones;

    private void initPhones() {
        if (Objects.isNull(phones))
            phones = new HashSet<>();
    }

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserDataSet(String name, int age, AddressDataSet address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void addPhone(PhoneDataSet phone) {
        Objects.requireNonNull(phone);
        initPhones();
        phone.setUser(this);
        phones.add(phone);
    }

    public void addPhones(Set<PhoneDataSet> phones) {
        Objects.requireNonNull(phones);
        initPhones();
        phones.forEach(phone -> phone.setUser(this));
        this.phones.addAll(phones);
    }

    public Set<PhoneDataSet> getPhones() {
        initPhones();
        return phones;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDataSet)) return false;
        if (!super.equals(o)) return false;
        UserDataSet that = (UserDataSet) o;
        return age == that.age &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, age);
    }

    @Override
    public String toString() {
        return "id=" + String.valueOf(getId()) +
                ", name='" + name + '\'' +
                ", age=" + age;
    }
}
