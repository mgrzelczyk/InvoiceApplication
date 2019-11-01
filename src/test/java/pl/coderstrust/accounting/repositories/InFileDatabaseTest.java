package pl.coderstrust.accounting.repositories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class InFileDatabaseTest {

    private Invoice invoice;
    private InFileDatabase inFileDatabase;
    private Invoice invoiceSecond;
    private String DATABASE_FILE_NAME = "database.db";

    @BeforeEach
    void setUp() {
        LocalDateTime date = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        invoice = new Invoice(
            2L,
            date,
            new Company(),
            new Company(),
            invoiceEntries);
    }

    @Test
    void shouldCreateInvoice() throws IOException {
        Invoice expected = new Invoice(null, null, null, null, null);
        Invoice result = inFileDatabase.findInvoiceById(null);
        assertEquals(expected, result);
    }

    @Test
    void shouldSaveInvoice() {
        Invoice savedInvoice = inFileDatabase.saveInvoice(invoice);
        List<String> invoices = inFileDatabase.findAllnvoices();;
        boolean invoiceExist = invoices.contains(invoice);
        Invoice expectedInvoice = new Invoice(2l, null, null, null, null);
        Invoice resultInvoice = new Invoice();
        resultInvoice.setId(2l);
        resultInvoice.setDate(null);
        resultInvoice.setBuyer(null);
        resultInvoice.setSeller(null);
        resultInvoice.setEntries(null);
        Long expectedId = 1l;
        Long resultIt = savedInvoice.getId();


        assertNull(savedInvoice);
        assertTrue(invoiceExist);
        assertEquals(expectedId, resultIt);
        assertEquals(expectedInvoice, resultInvoice);
    }

    @Test
    void shouldReturnSimpleInvoice() {
        Invoice expectedInvoice = new Invoice(2l, null, null, null, null);
        Invoice resultInvoice = new Invoice();
        resultInvoice.setId(2l);
        resultInvoice.setDate(null);
        resultInvoice.setBuyer(null);
        resultInvoice.setSeller(null);
        resultInvoice.setEntries(null);

        assertEquals(expectedInvoice, resultInvoice);
    }

    @Test
    void shouldGetIdInvoice() {
        Invoice savedInvoice = inFileDatabase.saveInvoice(invoice);
        Long expectedId = 1l;
        Long resultIt = savedInvoice.getId();

        assertEquals(expectedId, resultIt);
    }


    @Test
    void shouldFindInvoiceById() throws IOException {
        inFileDatabase.saveInvoice(this.invoice);
        Invoice invoiceById = inFileDatabase.findInvoiceById((long) 1);

        assertEquals(invoice, invoiceById);
    }

    @Test
    void shouldFindAllnvoices() {
        LocalDateTime date = LocalDateTime.of(2020, 2, 3, 4, 1, 1);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        invoiceSecond = new Invoice(
            2L,
            date,
            new Company(),
            new Company(),
            invoiceEntries);
        boolean result = false;
        ArrayList<Invoice> localInvoices = new ArrayList<>();
        localInvoices.add(invoice);
        localInvoices.add(invoiceSecond);

        inFileDatabase.saveInvoice(invoice);
        inFileDatabase.saveInvoice(invoiceSecond);
        List<String> invoices = inFileDatabase.findAllnvoices();;
        if (invoices.containsAll((Collection<?>) invoice) && invoices.containsAll((Collection<?>) invoiceSecond)) {
            result = true;
        }
        assertTrue(result);
        assertEquals(localInvoices, invoices);
    }

    @Test
    void shouldDeleteByInvoice() throws IOException {
        inFileDatabase.saveInvoice(invoice);
        Invoice deleted = inFileDatabase.deleteByInvoice((long) 1);
        System.out.println(deleted);
        List<String> invoices = inFileDatabase.findAllnvoices();

        boolean contains = invoices.contains(this.invoice);

        assertFalse(contains);
        assertEquals(invoice, deleted);
    }
}