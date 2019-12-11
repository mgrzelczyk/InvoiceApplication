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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.transaction.annotation.Transactional;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.mapper.InvoiceMapper;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.hibernate.CompanyHib;
import pl.coderstrust.accounting.model.hibernate.InvoiceEntryHib;
import pl.coderstrust.accounting.model.hibernate.InvoiceHib;
import pl.coderstrust.accounting.model.hibernate.VatHib;

@Transactional
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
abstract class DatabaseTests {

    abstract InvoiceDatabase getDatabase();

    @Test
    @DisplayName("Save invoice")
    void shouldSaveInvoiceWithNullId() throws NullPointerException {
        Invoice expected = prepareInvoice();
        Invoice result = getDatabase().saveInvoice(expected);
        List<Invoice> results = getDatabase().findAllInvoices();

        for (Invoice i : results) {
            System.out.println(i.toString());
        }

        assertThat(results.size(), is(1));
        assertNotNull(results.get(0).getId());
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
        Invoice newInvoice = prepareInvoice();

        Invoice expected = getDatabase().saveInvoice(invoice);
        newInvoice.setId(expected.getId());
        Invoice edited = getDatabase().saveInvoice(newInvoice);
        Invoice results = getDatabase().findInvoiceById(edited.getId());

        assertEquals(expected.getId(), results.getId());
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
        Invoice pieceExpected;
        List<Invoice> expected = new ArrayList<>();

        for (Invoice invoice : invoices) {
            expected.add(getDatabase().saveInvoice(invoice));
        }
        //List<Invoice> result = new ArrayList<>();
        List<Invoice> inside = getDatabase().findAllInvoices();
//        for (Invoice i : inside) {
//            for (Invoice x : expected) {
//                if (i.equals(x)) {
//                    result.add(i);
//                }
//            }
//        }
        assertEquals(expected.get(0), inside.get(0));
        assertEquals(expected.get(1), inside.get(1));
        assertEquals(expected.get(2), inside.get(2));
    }

    @Test
    @DisplayName("Should delete InvoiceHib by id")
    void shouldDeleteInvoiceByInvoice() {
        Invoice invoice = prepareInvoice();
        Invoice expected = getDatabase().saveInvoice(invoice);

        Invoice result = getDatabase().deleteInvoiceById(expected.getId());
        List<Invoice> invoices = getDatabase().findAllInvoices();

        for (Invoice i : invoices) {
            System.out.println(i.toString());
        }

        assertEquals(expected, result);
    }

    private Invoice prepareInvoice() {
        Random random = new Random();
        List<InvoiceEntryHib> invoiceEntries = new ArrayList<>();
        invoiceEntries.add(
            new InvoiceEntryHib("cos gdzies kiedys", new BigDecimal("2344"), 0, VatHib.TAX_FREE));
        CompanyHib buyer = prepareCompany("Wrocław 66-666", "TurboMarek z.o.o");
        CompanyHib seller = prepareCompany("Gdynia 66-666", "Szczupak z.o.o");
        InvoiceHib invoice = new InvoiceHib();
        invoice.setDate(LocalDate.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1));
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        Invoice invoice1 = InvoiceMapper.INSTANCE.toInvoice(invoice);
        return invoice1;
    }

    private CompanyHib prepareCompany(String city, String company) {
        Random random = new Random();
        return new CompanyHib(
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
