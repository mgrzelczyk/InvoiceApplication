package pl.coderstrust.accounting.services;

import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceBook {

    private final InvoiceDatabase invoiceDatabase;

    public InvoiceBook(InvoiceDatabase invoiceDatabase) {
        this.invoiceDatabase = invoiceDatabase;
    }

    public Invoice saveInvoice(Invoice invoice) throws IOException {
        if (invoice != null) {
            return invoiceDatabase.saveInvoice(invoice);
        }
        return null;
    }

    public Invoice findInvoiceById(Long id) throws IOException {
        Invoice invoiceFound = invoiceDatabase.findInvoiceById(id);
        if (invoiceFound != null) {
            return invoiceFound;
        }
        return null;
    }

    public List<Invoice> findAllInvoices() throws IOException {
        List<Invoice> invoices = new ArrayList<>(invoiceDatabase.findAllInvoices());
        if (!invoices.isEmpty()) {
            return invoices;
        }
        return null;
    }

    public Invoice deleteInvoiceById(Long id) throws IOException {
        Invoice invoiceFound = invoiceDatabase.findInvoiceById(id);
        if (invoiceFound != null) {
            return invoiceDatabase.deleteInvoiceById(id);
        }
        return null;
    }

}
