package pl.coderstrust.accounting.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceBook {

    private final InvoiceDatabase invoiceDatabase;

    private final Logger log = LoggerFactory.getLogger(InvoiceBook.class);

    public InvoiceBook(InvoiceDatabase invoiceDatabase) {
        this.invoiceDatabase = invoiceDatabase;
    }

    public Invoice saveInvoice(Invoice invoice) throws IOException {
        if (invoice != null) {
            log.info("[save] Invoice saved");
            return invoiceDatabase.saveInvoice(invoice);
        }
        log.warn("[save] Incorrect data");
        return null;
    }

    public Invoice findInvoiceById(Long id) throws IOException {
        Invoice invoiceFound = invoiceDatabase.findInvoiceById(id);
        if (invoiceFound != null) {
            log.info("[findId] Invoice found");
            return invoiceFound;
        }
        log.warn("[findId] Incorrect data or invoice doesn't exist");
        return null;
    }

    public List<Invoice> findAllInvoices() throws IOException {
        List<Invoice> invoices;
        invoices = new ArrayList<>(invoiceDatabase.findAllInvoices());
        if (invoices.size() >= 1) {
            log.info("[findAll] Invoices found");
            return invoices;
        }
        log.warn("[findAll] Invoices don't exist");
        return null;
    }

    public List<Invoice> findAllInvoiceByDateRange(LocalDate from, LocalDate to) throws IOException {
        List<Invoice> invoices = new ArrayList<>();
        for (Invoice invoice : invoiceDatabase.findAllInvoices()) {
            if (invoice.getDate().isAfter(from) && invoice.getDate().isBefore(to)
                || invoice.getDate().isEqual(from) || invoice.getDate().isEqual(to)) {
                invoices.add(invoice);
            }
        }
        if (!invoices.isEmpty()) {
            log.info("[findRange] Invoices found");
            return invoices;
        }
        log.info("[findRange] Invoices don't exist");
        return null;
    }

    public Invoice deleteInvoiceById(Long id) throws IOException {
        Invoice invoiceFound = invoiceDatabase.findInvoiceById(id);
        if (invoiceFound != null) {
            log.info("[delete] Invoice deleted");
            return invoiceDatabase.deleteInvoiceById(id);
        }
        log.warn("[delete] Invoice doesn't exist");
        return null;
    }

}
