package pl.coderstrust.accounting.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    private InMemoryDatabase inMemory;

    @BeforeEach()
    void createDataForTest() {
        inMemory = new InMemoryDatabase();
        Invoice example = new Invoice();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Random random = new Random();
        example.setId((long) 1);
        example.setDate(LocalDateTime.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(30) + 1,
            random.nextInt(12),
            random.nextInt(59) + 1,
            random.nextInt(59) + 1));
        example.setBuyer(new Company());
        example.setSeller(new Company());
        example.setEntries(invoiceEntries);
        testInvoices.add(example);
    }

    @Test
    void shouldCreateInvoice() {
        Invoice expected = testInvoices.get(0);
        Invoice result = inMemory.saveInvoice(expected);
        Invoice object = inMemory.findInvoiceById(1L);

        assertNull(result);
        assertEquals(expected, object);
    }

    @Test
    void shouldReturnSimpleInvoice() {
        Invoice expected = testInvoices.get(0);
        inMemory.saveInvoice(expected);
        Invoice object = inMemory.findInvoiceById(1L);
        Long objectId = object.getId();
        Long expectedId = expected.getId();

        assertEquals(expectedId, objectId);
        assertEquals(expected, object);
    }

    @Test
    void shouldFindObjectById() {
        Invoice expected = testInvoices.get(0);
        expected.setId(22L);
        inMemory.saveInvoice(expected);
        Invoice object = inMemory.findInvoiceById((long) 1);
        Long expectedId = expected.getId();
        Long objectId = object.getId();

        assertEquals(expectedId, objectId);
    }

    @Test
    void shouldFindAllnvoices() {
        Invoice example = new Invoice();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Random random = new Random();
        example.setId((long) 2);
        example.setDate(LocalDateTime.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(30) + 1,
            random.nextInt(12),
            random.nextInt(59) + 1,
            random.nextInt(59) + 1));
        example.setBuyer(new Company());
        example.setSeller(new Company());
        example.setEntries(invoiceEntries);
        testInvoices.add(example);
        int counter = 0;

        for (Invoice invoice : testInvoices) {
            inMemory.saveInvoice(invoice);
        }
        List<Invoice> allObjects = inMemory.findAllnvoices();

        assertEquals(testInvoices, allObjects);
    }

    @Test
    void shouldDeleteByInvoice() {
        inMemory.saveInvoice(testInvoices.get(0));
        Invoice deleted = inMemory.deleteByInvoice((long) 1);
        System.out.println(deleted);
        ArrayList<Invoice> invoices = (ArrayList<Invoice>) inMemory.findAllnvoices();

        boolean contains = invoices.contains(testInvoices.get(0));

        assertFalse(contains);
        assertEquals(testInvoices.get(0), deleted);
    }

}