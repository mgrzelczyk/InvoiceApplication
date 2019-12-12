package pl.coderstrust.accounting.repositories;

import org.apache.log4j.Logger;
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
    private final static Logger LOGGER = Logger.getLogger(InMemoryDatabase.class);

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        LOGGER.info("Save invoice in Memory Database");
        Invoice copiedInvoice = new Invoice(invoice);
        if (copiedInvoice.getId() == null) {
            LOGGER.info("Save invoice with null Id in Memory Database");
            Long id = counter.incrementAndGet();
            invoiceMap.put(id, copiedInvoice);
            LOGGER.info("ID is set");
            copiedInvoice.setId(id);
            return copiedInvoice;
        } else if (invoiceMap.containsKey(copiedInvoice.getId())) {
            LOGGER.info("Save invoice with Id in Memory Database");
            invoiceMap.put(copiedInvoice.getId(), copiedInvoice);
            return copiedInvoice;
        }
        throw new NullPointerException("There isn't option");
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        LOGGER.info("Find Invoice by Id in Memory Database");
        return invoiceMap.get(id);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        LOGGER.info("Find All Invoices by Id in Memory Database");
        Collection<Invoice> values = invoiceMap.values();
        return new ArrayList<>(values);
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        LOGGER.info("Delete Invoice by Id in Memory Database");
        return invoiceMap.remove(id);
    }

}
