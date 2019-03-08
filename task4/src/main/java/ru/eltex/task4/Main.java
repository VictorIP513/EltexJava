package ru.eltex.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eltex.task4.database.DBHelper;
import ru.eltex.task4.person.JuridicalPerson;
import ru.eltex.task4.person.NaturalPerson;
import ru.eltex.task4.person.Person;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        String taskNumber = "";
        do {
            printMenu();
            try {
                taskNumber = SCANNER.next();
                switch (taskNumber) {
                    case "1":
                        addJuridicalPersonToDatabase();
                        break;
                    case "2":
                        addNaturalPersonToDatabase();
                        break;
                    case "3":
                        deletePersonFromDatabase(JuridicalPerson.class);
                        break;
                    case "4":
                        deletePersonFromDatabase(NaturalPerson.class);
                        break;
                    case "5":
                        printAllPersonsFromDatabase(JuridicalPerson.class);
                        break;
                    case "6":
                        printAllPersonsFromDatabase(NaturalPerson.class);
                        break;
                    default:
                        break;
                }
            } catch (SQLException e) {
                LOGGER.error("SQLException: {}", e.getMessage());
            }
        } while (!taskNumber.equals("7"));
    }

    private static void printMenu() {
        LOGGER.info("\n");
        LOGGER.info("Menu:");
        LOGGER.info("1.Add Juridical Person to database");
        LOGGER.info("2.Add Natural Person to database");
        LOGGER.info("3.Delete Juridical Person from database");
        LOGGER.info("4.Delete Natural Person from database");
        LOGGER.info("5.Print all Juridical Persons from database");
        LOGGER.info("6.Print all Natural Persons from database");
        LOGGER.info("7.Exit");
    }

    private static void addJuridicalPersonToDatabase() throws SQLException {
        LOGGER.info("Enter name:");
        String name = SCANNER.next();
        LOGGER.info("Enter phone:");
        String phone = SCANNER.next();
        LOGGER.info("Enter legal address:");
        String legalAddress = SCANNER.next();
        if (!name.isEmpty() && !phone.isEmpty() && !legalAddress.isEmpty()) {
            JuridicalPerson juridicalPerson = new JuridicalPerson(name, phone, legalAddress);
            DBHelper.addPersonToDatabase(juridicalPerson);
        }
    }

    private static void addNaturalPersonToDatabase() throws SQLException {
        LOGGER.info("Enter name:");
        String name = SCANNER.next();
        LOGGER.info("Enter phone:");
        String phone = SCANNER.next();
        LOGGER.info("Enter physical address:");
        String physicalAddress = SCANNER.next();
        if (!name.isEmpty() && !phone.isEmpty() && !physicalAddress.isEmpty()) {
            NaturalPerson naturalPerson = new NaturalPerson(name, phone, physicalAddress);
            DBHelper.addPersonToDatabase(naturalPerson);
        }
    }

    private static void deletePersonFromDatabase(Class<? extends Person> personType) throws SQLException {
        try {
            LOGGER.info("Enter person id:");
            int id = SCANNER.nextInt();
            DBHelper.removePersonFromDatabase(id, personType);
        } catch (InputMismatchException ignored) {
            LOGGER.warn("Id must be int value");
        }
    }

    private static void printAllPersonsFromDatabase(Class<? extends Person> personType) throws SQLException {
        List<Person> personList = DBHelper.getAllPersonsFromDatabase(personType);
        for (Person person : personList) {
            LOGGER.info("{}", person);
        }
    }
}
