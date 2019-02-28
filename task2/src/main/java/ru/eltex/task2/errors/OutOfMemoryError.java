package ru.eltex.task2.errors;

import java.util.ArrayList;

public class OutOfMemoryError {

    public static void main(String[] args) {
        final int size = 1_000_000_000;
        new ArrayList<Integer>(size);
    }
}
