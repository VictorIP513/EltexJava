package ru.eltex.task1.calculator;

@FunctionalInterface
public interface CalculatorCommandListener {

    double calculate(double value1, double value2);
}
