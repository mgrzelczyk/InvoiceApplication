package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
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
        InFileInvoice invoiceExpected = new InFileInvoice();

        when(fileHelper.readLinesFromFile(DATABASE_FILE_NAME)).thenReturn((List<String>) inFileDatabase.saveInvoice(invoiceExpected));

        InFileInvoice invoiceResult = (InFileInvoice) fileHelper.readLinesFromFile(DATABASE_FILE_NAME);

        assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    void shouldGetLastId() throws IOException {
//        Long lastIdExpected = 1L;
//
//        when(inFileDatabase.getLastId(lastIdExpected).thenReturn(invoice));
//
//        Long lastIdInvoiceFound = inFileDatabase.getLastId();
//
//        assertEquals(lastIdExpected, lastIdInvoiceFound);
    }

    @Test
    void shouldFindInvoiceById() throws IOException {
//        Long idExpected = 1L;
//        Invoice invoiceExpected = new Invoice(1L, null, null, null, null);
//
//        when(inFileDatabase.findInvoiceById(idExpected)).thenReturn(invoice);
//
//        Invoice invoiceResult = inFileDatabase.findInvoiceById(idExpected);
//
//        assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    void shouldFindAllnvoices() throws IOException {
//        List<Invoice> listInvoiceExpected = new ArrayList<>();
//
//        when(inFileDatabase.findAllnvoices()).thenReturn(listInvoiceExpected);
//
//        List<Invoice> listInvoiceResult = inFileDatabase.findAllnvoices();
//
//        assertEquals(listInvoiceExpected, listInvoiceResult);
    }

    @Test
    void shouldDeleteByInvoice() throws IOException {
//        Long idDeleteInvoice = 1L;
//        Invoice invoiceExpected = new Invoice(1L, null, null, null, null);
//
//        when(inFileDatabase.deleteByInvoice(idDeleteInvoice)).thenReturn(invoice);
//
//        Invoice invoiceDeletedResult = inFileDatabase.deleteByInvoice(idDeleteInvoice);
//
//        assertEquals(invoiceExpected, invoiceDeletedResult);
    }
}
