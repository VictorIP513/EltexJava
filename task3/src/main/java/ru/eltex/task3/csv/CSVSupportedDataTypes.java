package ru.eltex.task3.csv;

import java.util.Arrays;

public enum CSVSupportedDataTypes {

    INT_PRIMITIVE(int.class, Integer::parseInt),
    FLOAT_PRIMITIVE(float.class, Float::parseFloat),
    DOUBLE_PRIMITIVE(double.class, Double::parseDouble),
    CHAR_PRIMITIVE(char.class, value -> value.charAt(0)),
    BYTE_PRIMITIVE(byte.class, Byte::parseByte),
    SHORT_PRIMITIVE(short.class, Short::parseShort),
    LONG_PRIMITIVE(long.class, Long::parseLong),
    BOOLEAN_PRIMITIVE(boolean.class, Boolean::parseBoolean),
    INTEGER(Integer.class, Integer::parseInt),
    FLOAT(Float.class, Float::parseFloat),
    DOUBLE(Double.class, Double::parseDouble),
    CHARACTER(Character.class, value -> value.charAt(0)),
    BYTE(Byte.class, Byte::parseByte),
    SHORT(Short.class, Short::parseShort),
    LONG(Long.class, Long::parseLong),
    BOOLEAN(Boolean.class, Boolean::parseBoolean),
    STRING(String.class, value -> value);

    private Class type;
    private StringParser stringParser;

    CSVSupportedDataTypes(Class type, StringParser stringParser) {
        this.type = type;
        this.stringParser = stringParser;
    }

    public static Object getParsedObject(String value, CSVSupportedDataTypes type) {
        return type.stringParser.parse(value);
    }

    public static CSVSupportedDataTypes getTypeFromClass(Class type) {
        return Arrays.stream(values())
                .filter(s -> s.getType() == type)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Type " + type.getName() + "is not supported"));
    }

    public static boolean isSupportDataType(Class type) {
        return Arrays.stream(values())
                .anyMatch(s -> s.getType() == type);
    }

    public Class getType() {
        return type;
    }
}
