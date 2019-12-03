package pl.coderstrust.accounting.repositories;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

    private final String databaseFileName;

    public FileHelper(String databaseFileName) {
        this.databaseFileName = databaseFileName;
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(databaseFileName, true))) {
            for (String str : lines) {
                bw.append(str);
                bw.newLine();
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
