package pl.coderstrust.accounting.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceBook {

    private final InvoiceDatabase invoiceDatabase;
    private final static Logger LOGGER = Logger.getLogger(InvoiceBook.class);

    public InvoiceBook(InvoiceDatabase invoiceDatabase) {
        this.invoiceDatabase = invoiceDatabase;
    }

    public Invoice saveInvoice(Invoice invoice) {
        if (invoice != null) {
            LOGGER.info("Save invoice in InvoiceBook services");
            return invoiceDatabase.saveInvoice(invoice);
        }
        LOGGER.info("Null save invoice in InvoiceBook services");
        return null;
    }

    public Invoice findInvoiceById(Long id) {
        Invoice invoiceFound = invoiceDatabase.findInvoiceById(id);
        if (invoiceFound != null) {
            LOGGER.info("Find Invoice by ID in InvoiceBook services");
            return invoiceFound;
        }
        LOGGER.info("Find Invoice by ID is null in InvoiceBook services");
        return null;
    }

    public List<Invoice> findAllInvoices() {
        List<Invoice> invoices;
        invoices = new ArrayList<>(invoiceDatabase.findAllInvoices());
        if (!invoices.isEmpty()) {
            LOGGER.info("Find all Invoices in InvoiceBook services");
            return invoices;
        }
        LOGGER.info("Null invoices finded in InvoiceBook services");
        return null;
    }

    public List<Invoice> findAllInvoiceByDateRange(LocalDate from, LocalDate to) {
        List<Invoice> invoices = new ArrayList<>();
        for (Invoice invoice : invoiceDatabase.findAllInvoices()) {
            LOGGER.info("Find all Invoice by data range in InvoiceBook services");
            if (invoice.getDate().isAfter(from) && invoice.getDate().isBefore(to)
                || invoice.getDate().isEqual(from) || invoice.getDate().isEqual(to)) {
                invoices.add(invoice);
            }
        }
        if (!invoices.isEmpty()) {
            LOGGER.info("Finded Invoice by data range in InvoiceBook services");
            return invoices;
        }
        LOGGER.info("Null finded Invoice by data range in InvoiceBook services");
        return null;
    }

    public Invoice deleteInvoiceById(Long id) {
        Invoice invoiceFound = invoiceDatabase.findInvoiceById(id);
        if (invoiceFound != null) {
            LOGGER.info("Delete Invoice by ID in InvoiceBook services");
            return invoiceDatabase.deleteInvoiceById(id);
        }
        LOGGER.info("Null deleted Invoice by ID in InvoiceBook services");
        return null;
    }

}
