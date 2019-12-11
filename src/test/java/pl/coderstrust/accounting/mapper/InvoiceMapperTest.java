package pl.coderstrust.accounting.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;
import pl.coderstrust.accounting.model.hibernate.CompanyHib;
import pl.coderstrust.accounting.model.hibernate.InvoiceEntryHib;
import pl.coderstrust.accounting.model.hibernate.InvoiceHib;
import pl.coderstrust.accounting.model.hibernate.VatHib;

@SpringBootTest
class InvoiceMapperTest {

    @Test
    private void shouldReturnInvoiceHib() {
        Invoice expected = prepareInvoice();
        InvoiceHib result = InvoiceMapper.INSTANCE.toInvoiceHib(expected);

        assertEquals(expected.getDate(), result.getDate());
    }

    @Test
    private void shouldReturnInvoice() {
        InvoiceHib expected = prepareInvoiceHib();
        Invoice result = InvoiceMapper.INSTANCE.toInvoice(expected);

        assertEquals(expected.getDate(), result.getDate());
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

    private InvoiceHib prepareInvoiceHib() {
        Random random = new Random();
        List<InvoiceEntryHib> invoiceEntries = new ArrayList<>();
        invoiceEntries.add(
            new InvoiceEntryHib("cos gdzies kiedys", new BigDecimal("2344"), 0, VatHib.TAX_FREE));
        CompanyHib buyer = prepareCompanyHib("Wrocław 66-666", "TurboMarek z.o.o");
        CompanyHib seller = prepareCompanyHib("Gdynia 66-666", "Szczupak z.o.o");
        InvoiceHib invoice = new InvoiceHib();
        invoice.setDate(LocalDate.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1));
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        return invoice;
    }

    private CompanyHib prepareCompanyHib(String city, String company) {
        Random random = new Random();
        return new CompanyHib(
            (long) (random.nextInt(10000) + 1),
            (random.nextInt(999999999) + 9999999) + "",
            city,
            company);
    }

}