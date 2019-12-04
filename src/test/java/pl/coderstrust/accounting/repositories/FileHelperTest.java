package pl.coderstrust.accounting.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class FileHelperTest {

    private FileHelper fileHelper;

    @BeforeClass
    private void copyFilesUsingStream(String source, String dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }

        } finally {
            is.close();
            os.close();
        }
    }

    @AfterClass
    public void clean() {
        File dir = new File("src/test/resources/temporary/");
        for (File file:dir.listFiles()) {
            file.delete();
        }
        dir.delete();
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
        String source = "src/test/resources/testFile";
        String dest = "src/test/resources/temporary/testFile";
        copyFilesUsingStream(source, dest);
        fileHelper = new FileHelper("src/test/resources/temporary/testFile");
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
