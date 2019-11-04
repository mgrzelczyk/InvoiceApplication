package pl.coderstrust.accounting.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

class InMemoryDatabaseTest {

    private static List<Invoice> testInvoices = new ArrayList<>();
    private final Random random = new Random();
    private InMemoryDatabase inMemory;
    private Invoice invoice;

    @BeforeEach
    void createDataForTest() {
        inMemory = new InMemoryDatabase();
        invoice = new Invoice();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        invoice.setId(1L);
        invoice.setDate(LocalDateTime.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1,
            random.nextInt(12),
            random.nextInt(59) + 1,
            random.nextInt(59) + 1));
        invoice.setBuyer(new Company());
        invoice.setSeller(new Company());
        invoice.setEntries(invoiceEntries);
        testInvoices.add(invoice);
    }

    @Test
    void shouldCheckInvoice() {
        assertNotNull(invoice);
    }

    @Test
    void shouldCreateInvoice() {
        Invoice expected = testInvoices.get(0);
        Invoice result = inMemory.saveInvoice(expected);
        Invoice found = inMemory.findInvoiceById(1L);

        assertNull(result);
        assertEquals(expected, found);
    }

    @Test
    void shouldReturnSimpleInvoice() {
        Invoice expected = testInvoices.get(0);
        inMemory.saveInvoice(expected);
        Invoice found = inMemory.findInvoiceById(1L);
        Long foundId = found.getId();
        Long expectedId = expected.getId();

        assertEquals(expectedId, foundId);
        assertEquals(expected, found);
    }

    @Test
    void shouldFindObjectById() {
        Invoice expected = testInvoices.get(0);
        expected.setId(22L);
        inMemory.saveInvoice(expected);
        Invoice found = inMemory.findInvoiceById(1L);
        Long expectedId = expected.getId();
        Long foundId = found.getId();

        assertEquals(expectedId, foundId);
    }

    @Test
    void shouldFindAllnvoices() {
        Invoice secondInvoice = new Invoice();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Random random = new Random();
        secondInvoice.setId(2L);
        secondInvoice.setDate(LocalDateTime.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1,
            random.nextInt(12),
            random.nextInt(59) + 1,
            random.nextInt(59) + 1));
        secondInvoice.setBuyer(new Company());
        secondInvoice.setSeller(new Company());
        secondInvoice.setEntries(invoiceEntries);
        testInvoices.add(secondInvoice);
        int counter = 0;

        for (Invoice inv : testInvoices) {
            inMemory.saveInvoice(inv);
        }

        List<Invoice> allInvoices = new ArrayList<>(inMemory.findAllInvoices().values());

        assertEquals(testInvoices, allInvoices);
    }

    @Test
    void shouldDeleteByInvoice() {
        inMemory.saveInvoice(testInvoices.get(0));
        Invoice deleted = inMemory.deleteInvoiceById(1L);

        List<Invoice> allInvoices = new ArrayList<>(inMemory.findAllInvoices().values());
        boolean contains = allInvoices.contains(testInvoices.get(0));

        assertFalse(contains);
        assertEquals(testInvoices.get(0), deleted);
    }

}