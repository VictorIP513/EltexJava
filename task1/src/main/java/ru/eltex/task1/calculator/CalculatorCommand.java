package ru.eltex.task1.calculator;

import java.util.Arrays;

public enum CalculatorCommand {

    PLUS("+", (value1, value2) -> value1 + value2),
    MINUS("+", (value1, value2) -> value1 - value2),
    MULTIPLY("*", (value1, value2) -> value1 * value2),
    DIVIDE("/", (value1, value2) -> value1 / value2);

    private String stringCommand;
    private CalculatorCommandListener calculatorCommandListener;

    @SuppressWarnings("unused")
    CalculatorCommand(String stringCommand, CalculatorCommandListener calculatorCommandListener) {
        this.stringCommand = stringCommand;
        this.calculatorCommandListener = calculatorCommandListener;
    }

    public static CalculatorCommand getCalculatorCommandFromStringCommand(String command) {
        return Arrays.stream(CalculatorCommand.values())
                .filter(s -> s.stringCommand.equals(command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Incorrect command: " + command));
    }

    public double calculate(double value1, double value2) {
        return calculatorCommandListener.calculate(value1, value2);
    }
}
