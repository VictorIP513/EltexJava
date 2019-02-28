package ru.eltex.task2.errors;

public class StackOverflowError {

    @SuppressWarnings("InfiniteRecursion")
    public static void main(String[] args) {
        main(args);
    }
}
