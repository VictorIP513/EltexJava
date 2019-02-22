package ru.eltex.task1.calculator;

public class Calculator {

    private Calculator() {

    }

    public static double calculate(double value1, double value2, CalculatorCommand calculatorCommand) {
        return calculatorCommand.calculate(value1, value2);
    }
}
