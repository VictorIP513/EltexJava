package ru.eltex.task3.csv;

interface StringParser<T> {

    T parse(String value);
}
