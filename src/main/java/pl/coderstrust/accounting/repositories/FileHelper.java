package pl.coderstrust.accounting.repositories;

import pl.coderstrust.accounting.application.DatabaseProperties;
import static pl.coderstrust.accounting.application.DatabaseProperties.databaseFileName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class FileHelper {

    private static String dbFile;

    public FileHelper(DatabaseProperties properties) {
        this.dbFile = databaseFileName;
    }

    public List<String> readLinesFromFile() throws IOException {
        List<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(databaseFileName))) {
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
        try (FileWriter writer = new FileWriter(databaseFileName)) {
            for (String str: lines) {
                writer.write(str);
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
