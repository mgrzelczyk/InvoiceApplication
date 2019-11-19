package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class InFileDatabaseTest {

    @Mock
    private FileHelper fileHelper;

    @Mock
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);

    InFileDatabaseTest() throws IOException {
    }

    @Test
    void shouldSaveInvoice() throws IOException {
        Invoice invoiceExpected = new Invoice();
        Invoice invoiceResult = new Invoice();
        List<String> invoiceListInput = new ArrayList<>();
        invoiceExpected.setId(1L);
        invoiceResult.setId(1L);
        String filePath = "database.db";

        Mockito.lenient().when(fileHelper.readLinesFromFile(filePath)).thenReturn(invoiceListInput);
        Mockito.lenient().when(inFileDatabase.saveInvoice(invoiceExpected)).thenCallRealMethod();

//        verify(fileHelper).readLinesFromFile(filePath);
//        verify(inFileDatabase).saveInvoice(invoiceExpected);
//        verify(fileHelper).writeLineToFile(filePath);

        assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    void shouldReturnNullForNullSave() {
    }

    @Test
    void shouldGetLastId() throws IOException {
        Invoice invoice = new Invoice();
        InFileInvoice inFileInvoiceExpected = new InFileInvoice();
        List<String> invoiceListInput = new ArrayList<>();
        inFileInvoiceExpected.setId(1L);
        String filePath = "database.db";

        when(inFileDatabase.getLastId()).thenReturn(1L);
    }

    @Test
    void shouldFindInvoiceById() throws IOException {
        Invoice invoice = new Invoice();
        InFileInvoice inFileInvoiceExpected = new InFileInvoice();
        List<String> invoiceListInput = new ArrayList<>();
        inFileInvoiceExpected.setId(1L);
        String filePath = "database.db";

        when(inFileDatabase.findInvoiceById(1L)).thenReturn(invoice);
    }

    @Test
    void shouldFindAllnvoices() throws IOException {
        List<Invoice> invoiceList = new ArrayList<>();

        when(inFileDatabase.findAllnvoices()).thenReturn(invoiceList);

        verify(inFileDatabase.findAllnvoices());
    }

    @Test
    void shouldDeleteByInvoice()  {
    }
}
