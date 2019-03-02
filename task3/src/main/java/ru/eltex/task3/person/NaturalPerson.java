package ru.eltex.task3.person;

import ru.eltex.task3.csv.CSVColumn;
import ru.eltex.task3.csv.CSVRecord;

public final class NaturalPerson extends Person implements CSVRecord {

    @CSVColumn(column = 3)
    private String physicalAddress;

    public NaturalPerson(int id, String name, String phone, String physicalAddress) {
        super(id, name, phone);
        this.physicalAddress = physicalAddress;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }
}
