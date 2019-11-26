package pl.coderstrust.accounting.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

class InMemoryDatabaseTest {

    private InMemoryDatabase inMemory;

    @BeforeEach
    void setUp() {
        inMemory = new InMemoryDatabase();
    }

    @Test
    @DisplayName("Save invoice")
    void shouldSaveInvoiceWithNullId() throws NullPointerException {
        Invoice expected = prepareInvoice();

        Invoice result = inMemory.saveInvoice(expected);
        List<Invoice> results = inMemory.findAllInvoices();

        assertThat(results.size(), is(1));
        assertNotNull(result.getId());
        assertEquals(expected.getDate(), result.getDate());
    }

    @Test
    @DisplayName("Save invoice as null")
    void shouldThrowExceptionForInvoiceAsNull() throws NullPointerException {
        Invoice invoice = null;

        assertThrows(NullPointerException.class, () -> inMemory.saveInvoice(invoice));
    }

    @Test
    @DisplayName("Update invoice")
    void shouldUpdateExistingInvoice() throws NullPointerException {
        Invoice invoice = prepareInvoice();
        Invoice expected = inMemory.saveInvoice(invoice);
        Invoice newInvoice = prepareInvoice();
        newInvoice.setId(expected.getId());

        Invoice result = inMemory.saveInvoice(newInvoice);
        List<Invoice> results = inMemory.findAllInvoices();

        assertThat(results.size(), is(1));
        assertEquals(expected.getId(), result.getId());
    }

    @Test
    @DisplayName("Update invoice with id but no other values. (Throw Exception)")
    void shouldThrowExceptionForInvoiceWithIdButNotExistInDb() throws NullPointerException {
        Invoice invoice = new Invoice();
        invoice.setId(2L);

        assertThrows(NullPointerException.class, () -> inMemory.saveInvoice(invoice));
    }

    @Test
    @DisplayName("Find invoice by id")
    void shouldFindInvoiceById() throws NullPointerException {
        Invoice invoice = prepareInvoice();

        Invoice expected = inMemory.saveInvoice(invoice);
        Invoice result = inMemory.findInvoiceById(expected.getId());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Find all invoices")
    void shouldFindAllInvoices() throws NullPointerException {
        List<Invoice> expected = prepareInvoices();

        for (Invoice invoice : expected) {
            inMemory.saveInvoice(invoice);
        }

        expected.get(0).setId(1L);
        expected.get(1).setId(2L);
        expected.get(2).setId(3L);

        List<Invoice> result = inMemory.findAllInvoices();

        assertEquals(expected, result);
        assertThat(result.size(), is(expected.size()));
    }

    @Test
    @DisplayName("Delete invoice")
    void shouldDeleteInvoiceByInvoice() {
        Invoice invoice = prepareInvoice();
        Invoice expected = inMemory.saveInvoice(invoice);

        Invoice result = inMemory.deleteInvoiceById(expected.getId());
        List<Invoice> invoices = inMemory.findAllInvoices();

        assertThat(invoices.size(), is(0));
        assertEquals(expected, result);
    }

    private Invoice prepareInvoice() {
        Random random = new Random();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Company buyer = prepareCompany("Wrocław 66-666", "TurboMarek z.o.o");
        Company seller = prepareCompany("Gdynia 66-666", "Szczupak z.o.o");
        Invoice invoice = new Invoice();
        invoice.setDate(LocalDateTime.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1,
            random.nextInt(12),
            random.nextInt(59) + 1,
            random.nextInt(59) + 1));
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        return invoice;
    }

    private Company prepareCompany(String city, String company) {
        Random random = new Random();
        return new Company(
            (long) (random.nextInt(10000) + 1),
            (random.nextInt(999999999) + 9999999) + "",
            city,
            company);
    }

    private List<Invoice> prepareInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice1 = prepareInvoice();
        invoices.add(invoice1);
        Invoice invoice2 = prepareInvoice();
        invoices.add(invoice2);
        Invoice invoice3 = prepareInvoice();
        invoices.add(invoice3);
        return invoices;
    }

}
