package pl.coderstrust.accounting.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.mapper.InvoiceMapper;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.hibernate.CompanyHibernate;
import pl.coderstrust.accounting.model.hibernate.InvoiceEntryHibernate;
import pl.coderstrust.accounting.model.hibernate.InvoiceHibernate;
import pl.coderstrust.accounting.model.hibernate.VatHibernate;

@Transactional
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
abstract class DatabaseTests {

    @Autowired
    private InvoiceMapper invoiceMapper;

    abstract InvoiceDatabase getDatabase();

    @Test
    @DisplayName("Save invoice")
    void shouldSaveInvoiceWithNullId() throws NullPointerException {
        Invoice expected = prepareInvoice();

        Invoice result = getDatabase().saveInvoice(expected);
        List<Invoice> results = getDatabase().findAllInvoices();

        assertThat(results.size(), is(1));
        assertNotNull(result.getId());
        assertEquals(expected.getDate(), result.getDate());
    }

    @Test
    @DisplayName("Save invoice as null")
    void shouldThrowExceptionForInvoiceAsNull() throws NullPointerException {
        assertThrows(NullPointerException.class, () -> getDatabase().saveInvoice(null));
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

        assertThat(results.size(), is(1));
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
        List<Invoice> invoices = prepareInvoices();
        List<Invoice> expected = new ArrayList<>();

        for (Invoice invoice : invoices) {
            expected.add(getDatabase().saveInvoice(invoice));
        }

        List<Invoice> result = getDatabase().findAllInvoices();

        assertEquals(expected, result);
        assertThat(result.size(), is(expected.size()));
    }

    @Test
    @DisplayName("Should delete InvoiceHibernate by id")
    void shouldDeleteInvoiceByInvoice() {
        Invoice invoice = prepareInvoice();
        Invoice expected = getDatabase().saveInvoice(invoice);

        Invoice result = getDatabase().deleteInvoiceById(expected.getId());

        assertEquals(expected, result);
    }

    private Invoice prepareInvoice() {
        Random random = new Random();
        List<InvoiceEntryHibernate> invoiceEntries = new ArrayList<>();
        invoiceEntries.add(
            new InvoiceEntryHibernate("cos gdzies kiedys", new BigDecimal("2344"),
                    0, VatHibernate.TAX_FREE));
        CompanyHibernate buyer = prepareCompany("Wrocław 66-666", "TurboMarek z.o.o");
        CompanyHibernate seller = prepareCompany("Gdynia 66-666", "Szczupak z.o.o");
        InvoiceHibernate invoice = new InvoiceHibernate();
        invoice.setDate(LocalDate.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1));
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        return invoiceMapper.toInvoice(invoice);
    }

    private CompanyHibernate prepareCompany(String city, String company) {
        Random random = new Random();
        return new CompanyHibernate(
            null,
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
