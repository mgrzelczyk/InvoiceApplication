package pl.coderstrust.accounting.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

@SpringBootTest
abstract class DatabaseTests {

    abstract InvoiceDatabase getDatabase();

    @Test
    @DisplayName("Save invoice")
    void shouldSaveInvoiceWithNullId() throws NullPointerException {
        Invoice expected = prepareInvoice();

        Invoice result = getDatabase().saveInvoice(expected);
        System.out.println(result);

        List<Invoice> results = getDatabase().findAllInvoices();

        assertThat(results.size(), is(1));
        assertNotNull(result.getId());
        assertEquals(expected.getDate(), result.getDate());
    }

    @Test
    @DisplayName("Save invoice as null")
    void shouldThrowExceptionForInvoiceAsNull() throws NullPointerException {
        Invoice invoice = null;

        assertThrows(NullPointerException.class, () -> getDatabase().saveInvoice(invoice));
    }

    @Test
    @DisplayName("Update invoice")
    void shouldUpdateExistingInvoice() throws NullPointerException {
        Invoice invoice = prepareInvoice();
        Invoice expected = getDatabase().saveInvoice(invoice);
        Invoice newInvoice = prepareInvoice();
        newInvoice.setId(expected.getId());

        Invoice result = getDatabase().saveInvoice(newInvoice);
        List<Invoice> results = getDatabase().findAllInvoices();

        assertThat(results.size(), is(3));
        assertEquals(expected.getId(), result.getId());
    }

    @Test
    @DisplayName("Update invoice with id but no other values. (Throw Exception)")
    void shouldThrowExceptionForInvoiceWithIdButNotExistInDb() throws NullPointerException {
        Invoice invoice = new Invoice();
        invoice.setId(2L);

        assertThrows(NullPointerException.class, () -> getDatabase().saveInvoice(invoice));
    }

    @Test
    @DisplayName("Find invoice by id")
    void shouldFindInvoiceById() throws NullPointerException {
        Invoice invoice = prepareInvoice();

        Invoice expected = getDatabase().saveInvoice(invoice);
        Invoice result = getDatabase().findInvoiceById(expected.getId());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Find all invoices")
    void shouldFindAllInvoices() throws NullPointerException {
        List<Invoice> expected = prepareInvoices();

        for (Invoice invoice : expected) {
            getDatabase().saveInvoice(invoice);
        }

        expected.get(0).setId(1L);
        expected.get(1).setId(2L);
        expected.get(2).setId(3L);

        List<Invoice> result = getDatabase().findAllInvoices();

        assertEquals(expected, result);
        assertThat(result.size(), is(expected.size()));
    }

    @Test
    @DisplayName("Should delete InvoiceHib by id")
    void shouldDeleteInvoiceByInvoice() {
        Invoice invoice = prepareInvoice();
        Invoice expected = getDatabase().saveInvoice(invoice);

        Invoice result = getDatabase().deleteInvoiceById(expected.getId());
        List<Invoice> invoices = getDatabase().findAllInvoices();

        for (Invoice i: invoices) {
            System.out.println(i.toString());
        }

        assertThat(invoices.size(), is(0));
        assertEquals(expected, result);
    }


    private Invoice prepareInvoice() {
        Random random = new Random();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Company buyer = prepareCompany("Wrocław 66-666", "TurboMarek z.o.o");
        Company seller = prepareCompany("Gdynia 66-666", "Szczupak z.o.o");
        Invoice invoice = new Invoice();
        invoice.setDate(LocalDate.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1));
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
