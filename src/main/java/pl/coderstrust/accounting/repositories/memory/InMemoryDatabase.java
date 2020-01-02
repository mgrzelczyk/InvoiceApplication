package pl.coderstrust.accounting.repositories.memory;

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
    private final Logger log = LoggerFactory.getLogger(InMemoryDatabase.class);

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        Invoice copiedInvoice = new Invoice(invoice);
        if (copiedInvoice.getId() == null) {
            Long id = counter.incrementAndGet();
            invoiceMap.put(id, copiedInvoice);
            copiedInvoice.setId(id);
            return copiedInvoice;
        } else if (invoiceMap.containsKey(copiedInvoice.getId())) {
            invoiceMap.put(copiedInvoice.getId(), copiedInvoice);
            return copiedInvoice;
        }
        log.warn("[save] Empty invoice");
        throw new NullPointerException();
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return invoiceMap.get(id);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        Collection<Invoice> values = invoiceMap.values();
        return new ArrayList<>(values);
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        return invoiceMap.remove(id);
    }

}
