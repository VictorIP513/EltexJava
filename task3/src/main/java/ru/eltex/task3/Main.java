package ru.eltex.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eltex.task3.csv.CSVFileManager;
import ru.eltex.task3.person.JuridicalPerson;
import ru.eltex.task3.person.NaturalPerson;
import ru.eltex.task3.person.Person;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final File file1 = new File("file1.csv");
    private static final File file2 = new File("file2.csv");

    public static void main(String[] args) {
        try {
            Person person = new NaturalPerson(1, "User1", "123456", "address1");
            CSVFileManager.write(file1, person, CSVFileManager.CSV_DEFAULT_DELIMITER);

            NaturalPerson personFromFile = CSVFileManager.readFirst(file1, NaturalPerson.class,
                    CSVFileManager.CSV_DEFAULT_DELIMITER);
            LOGGER.info("Id: {}", personFromFile.getId());
            LOGGER.info("Name: {}", personFromFile.getName());
            LOGGER.info("Phone: {}", personFromFile.getPhone());
            LOGGER.info("PhysicalAddress: {}", personFromFile.getPhysicalAddress());

            LOGGER.info("\n");

            for (int i = 0; i < 10; ++i) {
                Person juridicalPerson = new JuridicalPerson(i, "juridicalPerson " + i,
                        "123456", "address " + i);
                CSVFileManager.write(file2, juridicalPerson, CSVFileManager.CSV_DEFAULT_DELIMITER);
            }
            List<JuridicalPerson> personList = CSVFileManager.readAll(file2, JuridicalPerson.class,
                    CSVFileManager.CSV_DEFAULT_DELIMITER);
            for (JuridicalPerson juridicalPerson : personList) {
                LOGGER.info("Id: {}", juridicalPerson.getId());
                LOGGER.info("Name: {}", juridicalPerson.getName());
                LOGGER.info("Phone: {}", juridicalPerson.getPhone());
                LOGGER.info("LegalAddress: {}", juridicalPerson.getLegalAddress());
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
