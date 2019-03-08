package ru.eltex.task4.person;

public final class NaturalPerson extends Person {

    private String physicalAddress;

    public NaturalPerson(int id, String name, String phone, String physicalAddress) {
        super(id, name, phone);
        this.physicalAddress = physicalAddress;
    }

    public NaturalPerson(String name, String phone, String physicalAddress) {
        super(name, phone);
        this.physicalAddress = physicalAddress;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", 1physicalAddress: %s", physicalAddress);
    }
}
