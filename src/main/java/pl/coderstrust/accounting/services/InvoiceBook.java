package pl.coderstrust.accounting.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

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

    public List<Invoice> findAllnvoices() {
        ArrayList<Invoice> invoices = new ArrayList<>(invoiceDatabase.findAllnvoices().values());
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

    public Invoice editInvoice(Invoice invoice) {
        Invoice invoiceFound = invoiceDatabase.findInvoiceById(invoice.getId());
        if (invoiceFound != null) {
            return invoiceDatabase.saveInvoice(invoice);
        }
        return null;
    }

}


