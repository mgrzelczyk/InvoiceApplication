package pl.coderstrust.accounting.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryDatabase implements InvoiceDatabase {

    private Map<Long, Invoice> invoiceMap = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);
    private final static Logger log = LoggerFactory.getLogger(InMemoryDatabase.class);

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        log.info("Save invoice in Memory Database");
        Invoice copiedInvoice = new Invoice(invoice);
        if (copiedInvoice.getId() == null) {
            log.info("Save invoice with null Id in Memory Database");
            Long id = counter.incrementAndGet();
            invoiceMap.put(id, copiedInvoice);
            log.info("ID is set");
            copiedInvoice.setId(id);
            return copiedInvoice;
        } else if (invoiceMap.containsKey(copiedInvoice.getId())) {
            log.info("Save invoice with Id in Memory Database");
            invoiceMap.put(copiedInvoice.getId(), copiedInvoice);
            return copiedInvoice;
        }
        throw new NullPointerException("There isn't option");
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        log.info("Find Invoice by Id in Memory Database");
        return invoiceMap.get(id);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        log.info("Find All Invoices by Id in Memory Database");
        Collection<Invoice> values = invoiceMap.values();
        return new ArrayList<>(values);
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        log.info("Delete Invoice by Id in Memory Database");
        return invoiceMap.remove(id);
    }

}
