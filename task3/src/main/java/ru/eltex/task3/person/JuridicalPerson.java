package ru.eltex.task3.person;

import ru.eltex.task3.csv.CSVColumn;
import ru.eltex.task3.csv.CSVRecord;

public final class JuridicalPerson extends Person implements CSVRecord {

    @CSVColumn(column = 3)
    private String legalAddress;

    public JuridicalPerson(int id, String name, String phone, String legalAddress) {
        super(id, name, phone);
        this.legalAddress = legalAddress;
    }

    public String getLegalAddress() {
        return legalAddress;
    }
}
