package pl.coderstrust.accounting.repositories;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.accounting.application.DatabaseProperties;

import java.io.IOException;

class FileHelperTest {

    private FileHelper fileHelper;
    private String DATABASE_FILE_HELPER = "database.db";

    @BeforeEach
    void setup() {
        DatabaseProperties databaseProperties = new DatabaseProperties();
        fileHelper = new FileHelper(databaseProperties);
    }

    @Test
    void readLinesFromFileShouldThrowExceptionForNullFilePath() {
        assertThrows(NullPointerException.class,
            () -> {
                fileHelper.readLinesFromFile();
            });
    }

    @Test
    public void writeLinesToFileMethodShouldThrowExceptionForNullLines() throws IOException {
        assertThrows(IllegalArgumentException.class,
            () -> {
                fileHelper.writeLineToFile(null);
            });
    }

    @Test
    public void writeLineToFileMethodShouldThrowExceptionForNullLLine() throws IOException {
        assertThrows(IllegalArgumentException.class,
            () -> {
                fileHelper.writeLinesToFile(null);
            });
    }
}
