package ru.eltex.task4.database;

import java.sql.SQLException;
import java.sql.Statement;

public interface DatabaseActionListener {

    void executeAction(Statement statement) throws SQLException;
}
