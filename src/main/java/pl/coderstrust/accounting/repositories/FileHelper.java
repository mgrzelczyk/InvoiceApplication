package pl.coderstrust.accounting.repositories;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

    private static String separator = System.lineSeparator();
    private static String DATABASE_FILE_NAME = "database.db";

    public FileHelper(String databaseFilename) {
        if (DATABASE_FILE_NAME == null) {
            throw new IllegalArgumentException("File database cannot be null.");
        }
    }

    @SuppressWarnings("checkstyle:ParameterName")
    public List<String> readLinesFromFile(String DATABASE_FILE_NAME) throws IOException {
        List<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(DATABASE_FILE_NAME))) {
            while (scanner.hasNext()) {
                result.add(scanner.nextLine());
            }
        }
        return result;
    }

    public void writeLinesToFile(List<String> lines) throws IOException {
        if (lines == null) {
            throw new IllegalArgumentException("Lines cannot be null.");
        }
        try (FileWriter writer = new FileWriter(DATABASE_FILE_NAME)) {
            for (String str: lines) {
                writer.write(str);
                writer.write(separator);
            }
        }
    }

    public void writeLineToFile(String line) throws IOException {
        if (line == null) {
            throw new IllegalArgumentException("Line cannot be null.");
        }
        this.writeLinesToFile(Collections.singletonList(line));
    }
}
