package pl.coderstrust.accounting.repositories;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class FileHelper {

    public static final String databaseFileName = "database.db";

    public FileHelper() {
    }

    public List<String> readLinesFromFile() throws IOException {
        List<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(databaseFileName))) {
            while (scanner.hasNext()) {
                result.add(scanner.nextLine());
                scanner.close();
            }
        }
        return result;
    }

    public void writeLinesToFile(List<String> lines) throws IOException {
        if (lines == null) {
            throw new IllegalArgumentException("Lines cannot be null.");
        }
        try (FileWriter writer = new FileWriter(databaseFileName, true)) {
            BufferedWriter bw = new BufferedWriter(writer);
            for (String str: lines) {
                bw.append(str);
                bw.newLine();
                bw.close();
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
