package pl.coderstrust.accounting.repositories;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

class FileHelperTest {

    private FileHelper fileHelper;

    @BeforeClass
    private File createTemporaryFolder() {
        return new File("src/test/temporary/");
    }

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

    @AfterTest
    public void clean() throws IOException {
        File dir = new File("src/test/resources/temporary/testFile");
        FileUtils.forceDelete(dir);
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
        FileHelper fileHelper = new FileHelper(dest);
        List<String> stringsExpected = List.of("abc", "def");

        //when
        List<String> stringsResult = fileHelper.readLinesFromFile();

        //then
        assertEquals(stringsExpected, stringsResult);
    }

    @Test
    public void writeLinesToFileMethodShouldThrowExceptionForNullLines() throws IOException {
        // when, then
        assertThrows(NullPointerException.class,
            () -> {
                fileHelper.writeLineToFile(null);
            });
    }

    @Test
    void shouldWriteLinesToFile() throws IOException {
        //given
        String source = "src/test/resources/testWriteFile";
        String dest = "src/test/resources/temporary/testWriteFile";
        copyFilesUsingStream(source, dest);
        FileHelper fileHelper = new FileHelper(dest);
        String stringsExpected = "{\"id\":2,\"date\":whatever,\"buyer\":null," +
            "\"seller\":null,\"entries\":null}";

        //when
        fileHelper.writeLineToFile(stringsExpected);
        List<String> stringsList = fileHelper.readLinesFromFile();

        //then
        assertEquals(List.of(stringsExpected), stringsList);
    }

    @Test
    void shouldAppendLineToExistingFile() throws IOException {
        //given
        String source = "src/test/resources/testWriteFile2";
        String dest = "src/test/resources/temporary/testWriteFile2";
        copyFilesUsingStream(source, dest);
        FileHelper fileHelper = new FileHelper(dest);
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
        assertThrows(NullPointerException.class,
            () -> {
                fileHelper.writeLinesToFile(null);
            });
    }

}
