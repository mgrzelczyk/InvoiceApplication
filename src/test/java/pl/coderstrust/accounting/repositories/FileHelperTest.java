package pl.coderstrust.accounting.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class FileHelperTest {

    private FileHelper fileHelper;

    @BeforeEach
    void setup() {
        fileHelper = new FileHelper();
    }

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
        List<String> stringsExpected = new ArrayList<>();
        String jSon = "{\"id\":1,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}";
        stringsExpected.add(0, jSon);

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
        String stringsExpected = "{\"id\":2,\"date\":whatever,\"buyer\":null,\"seller\":null,\"entries\":null}";

        //when
        fileHelper.writeLineToFile(stringsExpected);
        List<String> stringsList = fileHelper.readLinesFromFile();
        String stringsResult = stringsList.stream().map(String::valueOf).collect(Collectors.joining());

        //then
        System.out.println(stringsResult);
        assertEquals(stringsExpected, stringsResult);
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
