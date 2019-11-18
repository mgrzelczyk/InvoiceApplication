package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class InFileDatabaseTest {

    private InFileInvoiceSerialize inFileInvoiceSerialize;
    private Invoice invoice;
    private String DATABASE_FILE_NAME = "database.db";

    @Mock
    private FileHelper fileHelper;
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);

    InFileDatabaseTest() throws IOException {
    }

    @Test
    void shouldSaveInvoice() throws IOException {
        Invoice invoice = new Invoice();
        InFileInvoice inFileInvoiceExpected = new InFileInvoice();
        List<String> invoiceListInput = new ArrayList<>();
        inFileInvoiceExpected.setId(1L);
        String filePath = "database.db";

        when(fileHelper.readLinesFromFile(filePath)).thenReturn(invoiceListInput);
        when(inFileDatabase.saveInvoice(invoice)).thenReturn(inFileInvoiceExpected);

        InFileInvoice inFileInvoiceResult = (InFileInvoice) fileHelper.readLinesFromFile(filePath);

        verify(fileHelper).readLinesFromFile(filePath);
        verify(inFileDatabase).saveInvoice(invoice);
        verify(fileHelper).writeLinesToFile(invoiceListInput);

        assertEquals(inFileInvoiceExpected, inFileInvoiceResult);
    }

    @Test
    void shouldReturnNullForNullSave() {
    }

    @Test
    void shouldGetLastId() {
    }

    @Test
    void shouldFindInvoiceById() {
    }

    @Test
    void shouldFindAllnvoices() {
    }

    @Test
    void shouldDeleteByInvoice()  {
    }
}
