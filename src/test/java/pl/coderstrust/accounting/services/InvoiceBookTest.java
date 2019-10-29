package pl.coderstrust.accounting.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.repositories.InMemoryDatabase;

@ExtendWith(MockitoExtension.class)
class InvoiceBookTest {

    @Mock
    InMemoryDatabase inMemoryDatabase;
    private Invoice invoiceExpected;
    @InjectMocks
    private InvoiceBook invoiceBook;

    @BeforeEach
    void setUp() {
        invoiceExpected = new Invoice();
        invoiceExpected.setId(1L);
    }

    private Map<Long, Invoice> prepareInvoiceData() {
        Map<Long, Invoice> invoices = new ConcurrentHashMap<>();
        Invoice invoice1 = new Invoice();
        invoice1.setId(12L);
        invoices.put(1L, invoice1);
        Invoice invoice2 = new Invoice();
        invoice2.setId(13L);
        invoices.put(2L, invoice2);
        Invoice invoice3 = new Invoice();
        invoice3.setId(14L);
        invoices.put(3L, invoice3);
        return invoices;
    }

    @Test
    void shouldSaveInvoice() throws NullPointerException {
        when(inMemoryDatabase.saveInvoice(invoiceExpected)).thenReturn(invoiceExpected);

        Invoice invoiceFound = invoiceBook.saveInvoice(invoiceExpected);

        assertEquals(invoiceExpected, invoiceFound);
    }

    @Test
    void shouldFindInvoiceById() throws NullPointerException {
        when(inMemoryDatabase.findInvoiceById(2L)).thenReturn(invoiceExpected);

        Invoice invoiceFound = invoiceBook.findInvoiceById(2L);

        assertEquals(invoiceExpected, invoiceFound);
    }

    @Test
    void shouldFindId() throws NullPointerException {
        when(inMemoryDatabase.findInvoiceById(2L)).thenReturn(invoiceExpected);

        Invoice invoiceFound = invoiceBook.findInvoiceById(2L);

        assertEquals(invoiceExpected.getId(), invoiceFound.getId());
    }

    @Test
    void shouldFindAllnvoicesListSize3() throws NullPointerException {
        Map<Long, Invoice> invoices = prepareInvoiceData();

        when(inMemoryDatabase.findAllnvoices()).thenReturn(invoices);

        List<Invoice> allInvoices = invoiceBook.findAllnvoices();

        assertThat(allInvoices, hasSize(3));
    }

    @Test
    void shouldFindAllInvoiceInRepository() throws NullPointerException {
        Map<Long, Invoice> invoices = prepareInvoiceData();
        List<Invoice> invoicesExpected = new ArrayList<>(invoices.values());

        when(inMemoryDatabase.findAllnvoices()).thenReturn(invoices);

        List<Invoice> allInvoices = invoiceBook.findAllnvoices();

        assertEquals(invoicesExpected, allInvoices);
    }

    @Test
    void shouldDeleteById() throws NullPointerException {
        when(inMemoryDatabase.deleteInvoiceById(invoiceExpected.getId())).thenReturn(
            invoiceExpected);

        Invoice deletedInvoice = invoiceBook.deleteInvoiceById(invoiceExpected.getId());

        assertEquals(invoiceExpected, deletedInvoice);
    }

    @Test
    void shouldEditInvoice() throws NullPointerException {
        Invoice editedInvoiceExpected = new Invoice();
        editedInvoiceExpected.setId(1L);
        editedInvoiceExpected.setDate(LocalDateTime.now());

        when(inMemoryDatabase.deleteInvoiceById(1L)).thenReturn(null);
        when(inMemoryDatabase.saveInvoice(editedInvoiceExpected)).thenReturn(editedInvoiceExpected);

        Invoice invoiceAfterEdit = invoiceBook.editInvoice(editedInvoiceExpected);

        assertEquals(editedInvoiceExpected, invoiceAfterEdit);
    }

}