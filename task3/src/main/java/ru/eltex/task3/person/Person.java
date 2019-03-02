package ru.eltex.task3.person;

import ru.eltex.task3.csv.CSVColumn;

public abstract class Person {

    @CSVColumn(column = 0)
    private int id;

    @CSVColumn(column = 1)
    private String name;

    @CSVColumn(column = 2)
    private String phone;

    Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

}
