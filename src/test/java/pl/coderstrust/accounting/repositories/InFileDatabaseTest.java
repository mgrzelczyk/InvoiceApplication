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
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class InFileDatabaseTest {

    private InFileInvoiceSerializer inFileInvoiceSerializer;
    private Invoice invoice;
    private String DATABASE_FILE_NAME = "database.db";

    @Mock
    private FileHelper fileHelper;
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private InFileDatabase inFileDatabase;

    @Test
    void shouldSaveInvoice() throws NullPointerException, IOException {
        InFileInvoice inFileInvoice = (InFileInvoice) new Invoice(1L, null, null, null, null);
        InFileInvoice invoiceExpected = (InFileInvoice) new Invoice();
        invoiceExpected.setId(1L);

        // when FileHelper read lines from file , then return
        // save invoice
        when(inFileDatabase.saveInvoice(invoice)).thenReturn(invoiceExpected);

        Invoice invoiceResult = (Invoice) fileHelper.readLinesFromFile(DATABASE_FILE_NAME);

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
        Long idExpected = 1L;
        Invoice invoiceExpected = new Invoice(1L, null, null, null, null);

        when(inFileDatabase.findInvoiceById(idExpected)).thenReturn(invoice);

        Invoice invoiceResult = inFileDatabase.findInvoiceById(idExpected);

        assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    void shouldFindAllnvoices() throws IOException {
        List<Invoice> listInvoiceExpected = new ArrayList<>();

        when(inFileDatabase.findAllnvoices()).thenReturn(listInvoiceExpected);

        List<Invoice> listInvoiceResult = inFileDatabase.findAllnvoices();

        assertEquals(listInvoiceExpected, listInvoiceResult);
    }

    @Test
    void shouldDeleteByInvoice() throws IOException {
        Long idDeleteInvoice = 1L;
        Invoice invoiceExpected = new Invoice(1L, null, null, null, null);

        when(inFileDatabase.deleteByInvoice(idDeleteInvoice)).thenReturn(invoice);

        Invoice invoiceDeletedResult = inFileDatabase.deleteByInvoice(idDeleteInvoice);

        assertEquals(invoiceExpected, invoiceDeletedResult);
    }
}
