package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.repositories.file.InFileDatabase;

import java.io.File;
import java.io.IOException;

@ActiveProfiles("file")
//@ContextConfiguration(locations = "classpath:application-in-file.properties")
//@ExtendWith(MockitoExtension.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
class InFileDatabaseTest extends DatabaseTests {

    @Autowired
    private InFileDatabase inFileDatabase;

    @Autowired
    ObjectMapper mapper;

    @Override
    InvoiceDatabase getDatabase() {
        return inFileDatabase;
    }

    @AfterEach
    public void clean() throws IOException {
        FileUtils.forceDelete(new File("database.db"));
    }

    @Test
    void test() throws IOException {
        Invoice invoice = new Invoice();
        String json = mapper.writeValueAsString(invoice);
        Invoice result = mapper.readValue(json, Invoice.class);
        assertEquals(invoice, result);
    }

}
