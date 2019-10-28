package pl.coderstrust.accounting.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.repositories.InMemoryDatabase;

@Service
public class InvoiceBook implements InvoiceDatabase {

    @Autowired
    private InMemoryDatabase inMemoryDatabase;
    private Logger log = Logger.getLogger(InvoiceBook.class.getName());

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return inMemoryDatabase.saveInvoice(invoice);
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        Invoice invoice = inMemoryDatabase.findInvoiceById(id);
        if(!invoice.equals(null)){
            return invoice;
        }
        log.info("Object not found");
        return null;
    }

    @Override
    public List<Invoice> findAllnvoices() {
        Collection<Invoice> allInvoice = inMemoryDatabase.findAllnvoices();
        List<Invoice> invoices = new ArrayList<>(allInvoice);
        return invoices;
    }

    @Override
    public Invoice deleteById(Long id) {
        Invoice deleteInvoice = inMemoryDatabase.findInvoiceById(id);
        if(!deleteInvoice.equals(null)){
            return inMemoryDatabase.deleteInvoiceById(id);
        }
        log.info("Object not found");
        return null;
    }

    @Override
    public Invoice editInvoice(Invoice invoice) {
        Invoice editInvoice = inMemoryDatabase.findInvoiceById(invoice.getId());
        if(!editInvoice.equals(null)){
            inMemoryDatabase.deleteInvoiceById(invoice.getId());
            inMemoryDatabase.saveInvoice(invoice);
            return invoice;
        }
        log.info("Object not found");
        return null;
    }

}
