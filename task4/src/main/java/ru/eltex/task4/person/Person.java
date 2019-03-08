package ru.eltex.task4.person;

public abstract class Person {

    private int id;
    private String name;
    private String phone;

    Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return String.format("Person: id: %s, name: %s, phone: %s", id, name, phone);
    }
}
