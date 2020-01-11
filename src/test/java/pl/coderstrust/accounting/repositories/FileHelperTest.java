package pl.coderstrust.accounting.repositories;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.accounting.repositories.file.FileHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

class FileHelperTest {

    private FileHelper fileHelper;

    @BeforeEach
    private void createTemporaryFolder() throws IOException {
        Files.createDirectories(Paths.get("src/test/resources/temporary/"));
    }

    private void copyFilesUsingStream(String sourceToCopy, String destCopied) throws IOException {
        Path source = Paths.get(sourceToCopy);
        Path dest = Paths.get(destCopied);
        Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterEach
    public void clean() throws IOException {
        File dir = new File("src/test/resources/temporary/");
        File resourcesDir = new File("src/test/resources/");
        FileUtils.forceDelete(dir);
        FileUtils.forceDelete(resourcesDir);
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
        File sourceFile = new File("src/test/resources/testFile");
        sourceFile.createNewFile();
        List<String> lines = new ArrayList<>();
        lines.add("abc\n");
        lines.add("def");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(source, true))) {
            for (String str : lines) {
                bw.append(str);
            }
        }

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
        File sourceFile = new File("src/test/resources/testWriteFile");
        sourceFile.createNewFile();

        copyFilesUsingStream(source, dest);
        FileHelper fileHelper = new FileHelper(dest);
        String stringsExpected = "{\"id\":2,\"date\":null,\"buyer\":null,"
            + "\"seller\":null,\"entries\":null}";

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
        File sourceFile = new File("src/test/resources/testWriteFile2");
        sourceFile.createNewFile();
        List<String> lines = new ArrayList<>();
        lines.add("abc");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(source, true))) {
            for (String str : lines) {
                bw.append(str);
            }
        }

        String dest = "src/test/resources/temporary/testWriteFile2";
        copyFilesUsingStream(source, dest);
        FileHelper fileHelper = new FileHelper(dest);
        String stringsNewLine = "\ndef";

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
