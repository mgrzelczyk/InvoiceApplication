package pl.coderstrust.accounting.repositories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

class FileHelperTest {

    private FileHelper fileHelper;
    private String DATABASE_FILE_HELPER = "database.db";

    @BeforeEach
    void setup() {
        fileHelper = new FileHelper(DATABASE_FILE_HELPER);
    }



    @Test
    void readLinesFromFileShouldThrowExceptionForNullFilePath() {
        assertThrows(IllegalArgumentException.class,
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
