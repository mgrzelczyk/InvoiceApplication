package pl.coderstrust.accounting.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.repositories.InMemoryDatabase;

@Service
public class InvoiceBook implements InvoiceDatabase {

    private final InMemoryDatabase inMemoryDatabase;

    @Autowired
    public InvoiceBook(InMemoryDatabase inMemoryDatabase) {
        this.inMemoryDatabase = inMemoryDatabase;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return inMemoryDatabase.saveInvoice(invoice);
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return inMemoryDatabase.findInvoiceById(id);
    }

    @Override
    public List<Invoice> findAllnvoices() {
        return new ArrayList<>(inMemoryDatabase.findAllnvoices().values());
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        return inMemoryDatabase.deleteInvoiceById(id);
    }

    @Override
    public Invoice editInvoice(Invoice invoice) {
        inMemoryDatabase.deleteInvoiceById(invoice.getId());
        inMemoryDatabase.saveInvoice(invoice);
        return invoice;
    }
}


