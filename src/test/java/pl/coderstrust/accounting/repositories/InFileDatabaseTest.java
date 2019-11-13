package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class InFileDatabaseTest {

    private InFileDatabase inFileDatabase;
    private Invoice invoice;
    private String DATABASE_FILE_NAME = "database.db";
    @Mock
    private FileHelper fileHelper;

    @BeforeEach
    void setUp() throws IOException {
        inFileDatabase = new InFileDatabase(fileHelper, new ObjectMapper());
        LocalDateTime date = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        invoice = new Invoice(1L, null, null, null, null);
    }

    @Test
    void shouldSaveInvoice() throws IOException {

        Invoice invoiceExpected = new Invoice(1L, null, null, null, null);
        Invoice invoiceResult = (Invoice) fileHelper.readLinesFromFile();

        when(inFileDatabase.saveInvoice(invoiceExpected)).thenReturn(invoiceResult);

        Invoice invoiceFound = inFileDatabase.saveInvoice(invoiceExpected);

        assertEquals(invoiceExpected, invoiceFound);
    }

    @Test
    void shouldGetLastId() throws IOException {
        Long lastIdExpected = 1L;

        //when(inFileDatabase.getLastId(lastIdExpected).thenReturn(lastIdExpected));

        //Long lastIdInvoiceFound = inFileDatabase.getLastId();

        //assertEquals(lastIdExpected, lastIdInvoiceFound);
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
