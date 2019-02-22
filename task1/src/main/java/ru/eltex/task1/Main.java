package ru.eltex.task1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eltex.task1.calculator.Calculator;
import ru.eltex.task1.calculator.CalculatorCommand;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER;
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    static {
        SCANNER = new Scanner(System.in);
    }

    public static void main(String[] args) {
        double value1;
        double value2;
        String operation;
        try {
            LOGGER.info("Enter first value:");
            value1 = SCANNER.nextDouble();
            LOGGER.info("Enter second value:");
            value2 = SCANNER.nextDouble();
            LOGGER.info("Enter operation (+, -, *, /)");
            operation = SCANNER.next();
            CalculatorCommand calculatorCommand = CalculatorCommand.getCalculatorCommandFromStringCommand(operation);
            LOGGER.info("Result: {} {} {} = {}", value1, operation, value2,
                    Calculator.calculate(value1, value2, calculatorCommand));
        } catch (InputMismatchException e) {
            LOGGER.error("Incorrect input value");
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
