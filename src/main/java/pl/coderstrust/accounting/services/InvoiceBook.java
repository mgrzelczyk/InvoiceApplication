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
        return invoiceDatabase.saveInvoice(invoice);
    }

    public Invoice findInvoiceById(Long id) {
        return invoiceDatabase.findInvoiceById(id);
    }

    public List<Invoice> findAllnvoices() {
        return new ArrayList<>(invoiceDatabase.findAllnvoices().values());
    }

    public Invoice deleteInvoiceById(Long id) {
        return invoiceDatabase.deleteInvoiceById(id);
    }

    public Invoice editInvoice(Invoice invoice) {
        invoiceDatabase.deleteInvoiceById(invoice.getId());
        invoiceDatabase.saveInvoice(invoice);
        return invoice;
    }

}


