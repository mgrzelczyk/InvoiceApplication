package pl.coderstrust.accounting.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FileHelperTest {

    private FileHelper fileHelper;

    @Test
    void readLinesFromFileShouldThrowExceptionForNullFilePath() {
        // when, then
        assertThrows(NullPointerException.class,
            () -> {
                fileHelper.readLinesFromFile();
            });
    }

    @Test
    void shouldReadLinesFromFile() throws IOException {
        //given
        fileHelper = new FileHelper("scr/test/resources/testFile");
        List<String> stringsExpected = List.of("abc", "def");

        //when
        List<String> stringsResult = fileHelper.readLinesFromFile();

        //then
        assertEquals(stringsExpected, stringsResult);
    }

    @Test
    public void writeLinesToFileMethodShouldThrowExceptionForNullLines() throws IOException {
        // when, then
        assertThrows(IllegalArgumentException.class,
            () -> {
                fileHelper.writeLineToFile(null);
            });
    }

    @Test
    void shouldWriteLinesToFile() throws IOException {
        //given
        FileHelper fileHelper = new FileHelper("scr/test/resources/testWriteFile");
        String stringsExpected = "{\"id\":2,\"date\":whatever,\"buyer\":null,\"seller\":null,\"entries\":null}";

        //when
        fileHelper.writeLineToFile(stringsExpected);
        List<String> stringsList = fileHelper.readLinesFromFile();


        //then
        assertEquals(List.of(stringsExpected), stringsList);
    }

    @Test
    void shouldAppendLineToExistingFile() throws IOException {
        //given
        FileHelper fileHelper = new FileHelper("scr/test/resources/testWriteFile2");
        String stringsNewLine = "def";

        //when
        fileHelper.writeLineToFile(stringsNewLine);
        List<String> stringsList = fileHelper.readLinesFromFile();
        
        //then
        assertEquals(List.of("abc", "def"), stringsList);
    }

    @Test
    public void writeLineToFileMethodShouldThrowExceptionForNullLLine() throws IOException {
        // when, then
        assertThrows(IllegalArgumentException.class,
            () -> {
                fileHelper.writeLinesToFile(null);
            });
    }

    @Test
    void shouldWriteLineToFile() throws IOException {
        //given
        List<String> stringsExpected = new ArrayList<>();
        String jSon = "{\"id\":3,\"date\":tomorrow,\"buyer\":null,\"seller\":null,\"entries\":null}";
        stringsExpected.add(0, jSon);

        //when
        fileHelper.writeLinesToFile(stringsExpected);
        List<String> stringsResult = fileHelper.readLinesFromFile();

        //then
        assertEquals(stringsExpected, stringsResult);
    }
}
