package ru.eltex.task4.database;

import ru.eltex.task4.person.JuridicalPerson;
import ru.eltex.task4.person.NaturalPerson;
import ru.eltex.task4.person.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private DBHelper() {

    }

    public static void addPersonToDatabase(Person person) throws SQLException {
        if (person instanceof JuridicalPerson) {
            openConnectionAndExecuteAction(statement -> {
                String sql = String.format(
                        "INSERT INTO juridicalPersons (name, phone, legalAddress) VALUES ('%s', '%s', '%s')",
                        person.getName(), person.getPhone(), ((JuridicalPerson) person).getLegalAddress());
                statement.executeUpdate(sql);
            });

        } else if (person instanceof NaturalPerson) {
            openConnectionAndExecuteAction(statement -> {
                String sql = String.format(
                        "INSERT INTO naturalPersons (name, phone, physicalAddress) VALUES ('%s', '%s', '%s')",
                        person.getName(), person.getPhone(), ((NaturalPerson) person).getPhysicalAddress());
                statement.executeUpdate(sql);
            });
        } else {
            throw new IllegalArgumentException("Database support only JuridicalPersons and NaturalPersons");
        }
    }

    public static void removePersonFromDatabase(int id, Class<? extends Person> type) throws SQLException {
        String tableName = getTableNameFromClass(type);
        openConnectionAndExecuteAction(statement -> {
            String sql = String.format("DELETE FROM %s WHERE id = %d", tableName, id);
            statement.executeUpdate(sql);
        });
    }

    public static List<Person> getAllPersonsFromDatabase(Class<? extends Person> personType) throws SQLException {
        final List<Person> personList = new ArrayList<>();
        String tableName = getTableNameFromClass(personType);
        openConnectionAndExecuteAction(statement -> {
            String sql = String.format("SELECT * FROM %s", tableName);
            ResultSet resultSet = statement.executeQuery(sql);
            if (personType == JuridicalPerson.class) {
                personList.addAll(juridicalPersonResultSetMapper(resultSet));
            } else {
                personList.addAll(naturalPersonResultSetMapper(resultSet));
            }

        });
        return personList;
    }

    private static String getTableNameFromClass(Class<? extends Person> type) {
        String tableName;
        if (type == JuridicalPerson.class) {
            tableName = "juridicalPersons";
        } else if (type == NaturalPerson.class) {
            tableName = "naturalPersons";
        } else {
            throw new IllegalArgumentException("Database support only JuridicalPersons and NaturalPersons");
        }
        return tableName;
    }

    private static void openConnectionAndExecuteAction(DatabaseActionListener actionListener) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                DatabaseProperties.getProperty("database_connection_url"),
                DatabaseProperties.getProperty("database_login"),
                DatabaseProperties.getProperty("database_password"));
             Statement statement = connection.createStatement()) {
            actionListener.executeAction(statement);
        }
    }

    private static List<JuridicalPerson> juridicalPersonResultSetMapper(ResultSet resultSet) throws SQLException {
        List<JuridicalPerson> juridicalPersonList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String phone = resultSet.getString("phone");
            String legalAddress = resultSet.getString("legalAddress");
            juridicalPersonList.add(new JuridicalPerson(id, name, phone, legalAddress));
        }
        return juridicalPersonList;
    }

    private static List<NaturalPerson> naturalPersonResultSetMapper(ResultSet resultSet) throws SQLException {
        List<NaturalPerson> naturalPersonList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String phone = resultSet.getString("phone");
            String physicalAddress = resultSet.getString("physicalAddress");
            naturalPersonList.add(new NaturalPerson(id, name, phone, physicalAddress));
        }
        return naturalPersonList;
    }
}
