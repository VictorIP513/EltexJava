package ru.eltex.task4.person;

public final class JuridicalPerson extends Person {

    private String legalAddress;

    public JuridicalPerson(int id, String name, String phone, String legalAddress) {
        super(id, name, phone);
        this.legalAddress = legalAddress;
    }

    public JuridicalPerson(String name, String phone, String legalAddress) {
        super(name, phone);
        this.legalAddress = legalAddress;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", legalAddress: %s", legalAddress);
    }
}
