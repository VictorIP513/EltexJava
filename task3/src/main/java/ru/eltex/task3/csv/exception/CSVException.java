package ru.eltex.task3.csv.exception;

abstract class CSVException extends RuntimeException {

    CSVException(String message) {
        super(message);
    }
}
