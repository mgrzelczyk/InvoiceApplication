package pl.coderstrust.accounting.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceBook {

    private final InvoiceDatabase invoiceDatabase;

    public InvoiceBook(InvoiceDatabase invoiceDatabase) {
        this.invoiceDatabase = invoiceDatabase;
    }

    public Invoice saveInvoice(Invoice invoice) {
        if (invoice != null) {
            return invoiceDatabase.saveInvoice(invoice);
        }
        return null;
    }

    public Invoice findInvoiceById(Long id) {
        Invoice invoiceFound = invoiceDatabase.findInvoiceById(id);
        if (invoiceFound != null) {
            return invoiceFound;
        }
        return null;
    }

    public List<Invoice> findAllInvoices() {
        List<Invoice> invoices;
        invoices = new ArrayList<>(invoiceDatabase.findAllInvoices());
        if (!invoices.isEmpty()) {
            return invoices;
        }
        return null;
    }

    public List<Invoice> findAllInvoiceByDateRange(LocalDate from, LocalDate to) {
        List<Invoice> invoices = new ArrayList<>();
        for (Invoice invoice : invoiceDatabase.findAllInvoices()) {
            if (invoice.getDate().isAfter(from) && invoice.getDate().isBefore(to)
                || invoice.getDate().isEqual(from) || invoice.getDate().isEqual(to)) {
                invoices.add(invoice);
            }
        }
        if (!invoices.isEmpty()) {
            return invoices;
        }
        return null;
    }

    public Invoice deleteInvoiceById(Long id) {
        Invoice invoiceFound = invoiceDatabase.findInvoiceById(id);
        if (invoiceFound != null) {
            return invoiceDatabase.deleteInvoiceById(id);
        }
        return null;
    }

}
