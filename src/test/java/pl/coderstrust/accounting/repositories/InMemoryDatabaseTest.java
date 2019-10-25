package pl.coderstrust.accounting.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

class InMemoryDatabaseTest {

    private Invoice invoice;
    private InMemoryDatabase inMemory;

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
        inMemory = new InMemoryDatabase();
    }

    @Test

    void shouldCreateInvoice() {
        Invoice expected = new Invoice();
        Invoice result = inMemory.invoice;
        assertEquals(expected, result);
    }

    @Test
    void shouldSaveInvoice() {
        Invoice savedInvoice = inMemory.saveInvoice(invoice);
        ArrayList<Invoice> invoices = (ArrayList<Invoice>) inMemory.findAllnvoices();
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
        Invoice savedInvoice = inMemory.saveInvoice(invoice);
        Long expectedId = 1l;
        Long resultIt = savedInvoice.getId();

        assertEquals(expectedId, resultIt);
    }


    @Test
    void shouldFindInvoiceById() {
        inMemory.saveInvoice(this.invoice);
        Invoice invoiceById = inMemory.findInvoiceById((long) 1);

        assertEquals(invoice, invoiceById);
    }

    @Test
    void shouldFindAllnvoices() {
        LocalDateTime date = LocalDateTime.of(2020, 2, 3, 4, 1, 1);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Invoice invoice2 = new Invoice(
            (long) 2,
            date,
            new Company(),
            new Company(),
            invoiceEntries);
        boolean result = false;
        ArrayList<Invoice> localInvoices = new ArrayList<>();
        localInvoices.add(invoice);
        localInvoices.add(invoice2);

        inMemory.saveInvoice(invoice);
        inMemory.saveInvoice(invoice2);
        ArrayList<Invoice> invoices = (ArrayList<Invoice>) inMemory.findAllnvoices();
        if (invoices.containsAll((Collection<?>) invoice) && invoices.containsAll((Collection<?>) invoice2)) {
            result = true;
        }

        assertTrue(result);
        assertEquals(localInvoices, invoices);
    }

    @Test
    void shouldDeleteByInvoice() {
        inMemory.saveInvoice(invoice);
        Invoice deleted = inMemory.deleteByInvoice((long) 1);
        System.out.println(deleted);
        ArrayList<Invoice> invoices = (ArrayList<Invoice>) inMemory.findAllnvoices();

        boolean contains = invoices.contains(this.invoice);

        assertFalse(contains);
        assertEquals(invoice, deleted);
    }

}