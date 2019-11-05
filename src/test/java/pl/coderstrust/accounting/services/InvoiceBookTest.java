package pl.coderstrust.accounting.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;
import pl.coderstrust.accounting.repositories.InMemoryDatabase;

@ExtendWith(MockitoExtension.class)
class InvoiceBookTest {

    @Mock
    private InMemoryDatabase inMemoryDatabase;

    @InjectMocks
    private InvoiceBook invoiceBook;

    @Test
    @DisplayName("Save invoice")
    void shouldSaveInvoice() throws NullPointerException {
        Invoice invoice = prepareInvoice();
        Invoice expected = prepareInvoice();

        when(inMemoryDatabase.saveInvoice(invoice)).thenReturn(expected);

        Invoice invoiceFound = invoiceBook.saveInvoice(invoice);

        assertEquals(expected, invoiceFound);
    }

    @Test
    @DisplayName("Save invoice with id but not exist in db")
    void shouldThrowExceptionWhenSavedInvoiceDoesntExistInDatabase() throws NullPointerException {
        Invoice invoice = prepareInvoice();

        when(inMemoryDatabase.saveInvoice(invoice)).thenReturn(invoice);
        when(inMemoryDatabase.findInvoiceById(invoice.getId()))
            .thenThrow(NullPointerException.class);

        Invoice invoiceSave = invoiceBook.saveInvoice(invoice);

        assertThrows(NullPointerException.class, () ->
            invoiceBook.findInvoiceById(invoiceSave.getId()));
    }

    @Test
    @DisplayName("Throw exception when save doesnt work")
    void shouldSaveInvoiceAndReturnNull() throws NullPointerException {
        Invoice invoice = prepareInvoice();

        when(inMemoryDatabase.saveInvoice(invoice)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> invoiceBook.saveInvoice(invoice));
    }

    @Test
    @DisplayName("Save null invoice and return null")
    void shouldNotSaveInvoiceAndReturnNull() throws NullPointerException {
        assertNull(invoiceBook.saveInvoice(null));
    }

    @Test
    @DisplayName("Find invoice by id")
    void shouldFindInvoiceById() throws NullPointerException {
        Invoice expected = prepareInvoice();
        Invoice invoice = prepareInvoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenReturn(expected);

        Invoice invoiceFound = invoiceBook.findInvoiceById(invoice.getId());

        assertEquals(expected, invoiceFound);
    }

    @Test
    @DisplayName("Not Find invoice by id")
    void shouldNotFindInvoiceById() throws NullPointerException {
        Invoice invoice = prepareInvoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenReturn(null);

        Invoice invoiceFound = invoiceBook.findInvoiceById(invoice.getId());

        assertNull(invoiceFound);
    }

    @Test
    @DisplayName("Throw exception when invoice doesnt exist")
    void shouldThrowExceptionWhenInvoiceDoesntExist() throws NullPointerException {
        when(inMemoryDatabase.findInvoiceById(1L)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> invoiceBook.findInvoiceById(1L));
    }

    @Test
    @DisplayName("Find id invoice")
    void shouldFindInvoiceId() throws NullPointerException {
        Invoice invoice = prepareInvoice();
        Invoice expected = prepareInvoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenReturn(expected);

        Invoice invoiceFound = invoiceBook.findInvoiceById(invoice.getId());

        assertEquals(expected.getId(), invoiceFound.getId());
    }

    @Test
    @DisplayName("Find 3 invoices")
    void shouldFindAllInvoicesListSize3() throws NullPointerException {
        List<Invoice> invoices = prepareInvoices();

        when(inMemoryDatabase.findAllInvoices()).thenReturn(invoices);

        List<Invoice> allInvoices = invoiceBook.findAllInvoices();

        assertThat(allInvoices, hasSize(3));
    }

    @Test
    @DisplayName("Find all invoices")
    void shouldFindAllInvoiceInRepository() throws NullPointerException {
        List<Invoice> invoices = prepareInvoices();
        List<Invoice> invoicesExpected = new ArrayList<>(invoices);

        when(inMemoryDatabase.findAllInvoices()).thenReturn(invoices);

        List<Invoice> allInvoices = invoiceBook.findAllInvoices();

        assertEquals(invoicesExpected, allInvoices);
    }

    @Test
    @DisplayName("Not find all invoices")
    void shouldNotFindAllInvoiceInRepository() throws NullPointerException {
        List<Invoice> invoices = new ArrayList<>();

        when(inMemoryDatabase.findAllInvoices()).thenReturn(invoices);

        assertNull(invoiceBook.findAllInvoices());
    }

    @Test
    @DisplayName("Delete by id")
    void shouldDeleteById() throws NullPointerException {
        Invoice invoice = prepareInvoice();
        Invoice expected = prepareInvoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenReturn(expected);
        when(inMemoryDatabase.deleteInvoiceById(expected.getId())).thenReturn(
            expected);

        Invoice deletedInvoice = invoiceBook.deleteInvoiceById(invoice.getId());

        assertEquals(expected, deletedInvoice);
    }

    @Test
    @DisplayName("Delete by id, not find invoice")
    void shouldNotFindObjectForDeleteById() throws NullPointerException {
        Invoice invoice = prepareInvoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenReturn(null);

        Invoice deletedInvoice = invoiceBook.deleteInvoiceById(invoice.getId());

        assertNull(deletedInvoice);
    }

    @Test
    @DisplayName("Throw exception when delete by id doesnt work")
    void shouldThrowExceptionWhenDeleteInvoiceNotExist() throws NullPointerException {
        Invoice invoice = prepareInvoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId()))
            .thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () ->
            invoiceBook.deleteInvoiceById(invoice.getId()));
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
