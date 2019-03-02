package ru.eltex.task3.csv;

import ru.eltex.task3.csv.exception.CSVClassParserException;
import ru.eltex.task3.csv.exception.CSVReaderException;
import ru.eltex.task3.csv.exception.CSVWriterException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class CSVFileManager {

    public static final String CSV_DEFAULT_DELIMITER = ",";

    private static final String CSV_DELIMITER_REGEXP = "(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    private CSVFileManager() {

    }

    public static <T> T readFirst(File file, Class<T> type, String delimiter) throws IOException {
        String record = readFirstRecordFromFile(file);
        List<String> records = new ArrayList<>();
        records.add(record);
        List<T> outputObjects = createOutputObjects(records, type, delimiter);
        return outputObjects.get(0);
    }

    public static <T> List<T> readAll(File file, Class<T> type, String delimiter) throws IOException {
        List<String> records = Files.readAllLines(file.toPath());
        return createOutputObjects(records, type, delimiter);
    }

    public static <T> void write(File file, T object, String delimiter) throws IOException {
        Class<?> type = object.getClass();
        Map<Integer, Field> columns = getColumns(type);
        checkCorrectClasses(type);
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : columns.values()) {
            try {
                field.setAccessible(true);
                String fieldValue = String.valueOf(field.get(object));
                if (fieldValue.contains(delimiter)) {
                    stringBuilder.append("\"").append(fieldValue).append("\"");
                } else {
                    stringBuilder.append(fieldValue);
                }
                stringBuilder.append(delimiter);
            } catch (IllegalAccessException e) {
                throw new CSVWriterException(e.getMessage());
            }
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        stringBuilder.append("\n");
        writeStringToFile(file, stringBuilder.toString());
    }

    private static <T> List<T> createOutputObjects(List<String> inputRecords, Class<T> type, String delimiter) {
        checkCorrectClasses(type);

        Map<Integer, Field> columns = getColumns(type);
        Field[] fields = columns.values().toArray(new Field[0]);

        List<String[]> values = new ArrayList<>(inputRecords.size());
        for (String record : inputRecords) {
            values.add(splitCSVValues(record, delimiter));
        }

        if (fields.length != values.get(0).length) {
            throw new CSVReaderException("The number of columns should be " + values.get(0).length);
        }
        Class<?>[] fieldsType = new Class[fields.length];
        for (int i = 0; i < fieldsType.length; ++i) {
            fieldsType[i] = fields[i].getType();
        }

        try {
            Constructor<T> constructor = type.getConstructor(fieldsType);
            List<Object[]> valuesToConstructor = new ArrayList<>(values.size());
            for (String[] recordValues : values) {
                valuesToConstructor.add(parseInputData(recordValues, fields));
            }
            List<T> resultObjects = new ArrayList<>(valuesToConstructor.size());
            for (Object[] constructorValues : valuesToConstructor) {
                resultObjects.add(constructor.newInstance(constructorValues));
            }
            return resultObjects;
        } catch (NoSuchMethodException ignored) {
            throw new CSVWriterException("No public constructor found for input data");
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new CSVWriterException("Failed to create output object: " + e.getMessage());
        }
    }

    private static void checkCorrectClasses(Class<?> type) {
        boolean findCSVRecordInterface = false;
        Class<?>[] classInterfaces = type.getInterfaces();
        for (Class<?> classInterface : classInterfaces) {
            if (classInterface == CSVRecord.class) {
                findCSVRecordInterface = true;
                break;
            }
        }
        if (!findCSVRecordInterface) {
            throw new CSVClassParserException(String.format("Class %s does not implements interface %s",
                    type.getName(), CSVRecord.class.getName()));
        }

        Map<Integer, Field> columns = getColumns(type);
        checkCorrectColumnsIds(columns.keySet());
        checkCorrectFieldsType(columns.values());
    }

    private static String[] splitCSVValues(String record, String delimiter) {
        String[] values = record.split(delimiter + CSV_DELIMITER_REGEXP);
        for (int i = 0; i < values.length; ++i) {
            String value = values[i];
            if (value.contains(delimiter)) {
                value = value.substring(1).substring(0, value.length() - 1);
                values[i] = value;
            }
        }
        return values;
    }

    private static Object[] parseInputData(String[] values, Field[] valuesType) {
        Object[] parsedValues = new Object[values.length];
        for (int i = 0; i < values.length; ++i) {
            CSVSupportedDataTypes valueType = CSVSupportedDataTypes.getTypeFromClass(valuesType[i].getType());
            parsedValues[i] = CSVSupportedDataTypes.getParsedObject(values[i], valueType);
        }
        return parsedValues;
    }

    private static void writeStringToFile(File file, String str) throws IOException {
        if (file.exists()) {
            Files.write(file.toPath(), str.getBytes(), StandardOpenOption.APPEND);
        } else {
            Files.write(file.toPath(), str.getBytes());
        }
    }

    private static String readFirstRecordFromFile(File file) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.readLine();
        }
    }

    private static Map<Integer, Field> getColumns(Class type) {
        Map<Integer, Field> columns = new TreeMap<>();
        List<Class> classes = getSuperClassesFromClass(type);
        classes.add(type);
        for (Class c : classes) {
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType() != CSVColumn.class) {
                        continue;
                    }
                    int column = ((CSVColumn) annotation).column();
                    if (columns.containsKey(column)) {
                        throw new CSVWriterException("Column id must be unique");
                    } else {
                        columns.put(column, field);
                    }
                }
            }
        }
        return columns;
    }

    private static void checkCorrectColumnsIds(Set<Integer> columnsIds) {
        int counter = 0;
        for (int columnId : columnsIds) {
            if (columnId != counter) {
                throw new CSVClassParserException(String.format("Incorrect column id: %d. Expected id: %d",
                        columnId, counter));
            }
            ++counter;
        }
    }

    private static void checkCorrectFieldsType(Collection<Field> fields) {
        for (Field field : fields) {
            if (!CSVSupportedDataTypes.isSupportDataType(field.getType())) {
                throw new CSVClassParserException(String.format("Type %s is not supported for storage in CSV file",
                        field.getType().getName()));
            }
        }
    }

    private static List<Class> getSuperClassesFromClass(Class c) {
        if (c == Object.class) {
            return Collections.emptyList();
        }
        List<Class> classes = new ArrayList<>();
        Class superClass = c;
        while (superClass != Object.class) {
            superClass = superClass.getSuperclass();
            classes.add(superClass);
        }
        return classes;
    }
}
