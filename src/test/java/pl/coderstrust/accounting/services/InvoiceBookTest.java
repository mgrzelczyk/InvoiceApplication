package pl.coderstrust.accounting.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.repositories.InMemoryDatabase;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceBookTest {

    //private Invoice invoice;

    private Invoice invoice;
    
    @Mock
    InMemoryDatabase inMemoryDatabase;

    @InjectMocks
    private InvoiceBook invoiceBook;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        invoice = new Invoice();
        invoice.setId(12L);
    }

    @Test
    public void shouldSaveInvoice() throws Exception {

        when(invoiceBook.saveInvoice(invoice)).thenReturn(invoice);

        Invoice returnedInvoice = invoiceBook.saveInvoice(invoice);

        assertEquals(invoice, returnedInvoice);
    }

    @Test
    public void shouldFindInvoiceById() {

        when(invoiceBook.findInvoiceById(2L)).thenReturn(invoice);

        Invoice invoiceFound = invoiceBook.findInvoiceById(2L);

        assertEquals(invoice, invoiceFound);
    }

    @Test
    public void shouldFindAllnvoices() {
    }

    @Test
    public void shouldDeleteById() {
    }

    @Test
    public void shouldEditInvoice() {
    }
}