package pl.coderstrust.accounting.repositories;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.services.InvoiceBook;

@Repository
public class InMemoryDatabase {

    private Map<Long, Invoice> invoiceMap = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);
    private Logger log = Logger.getLogger(InvoiceBook.class.getName());

    public Invoice saveInvoice(Invoice invoice) {
        Long id = counter.incrementAndGet();
        log.info(id + "");
        return invoiceMap.put(id, invoice);
    }

    public Invoice findInvoiceById(Long id) {
        return invoiceMap.get(id);
    }

    public Collection<Invoice> findAllnvoices() {
        return (Collection<Invoice>) invoiceMap;
    }

    public Invoice deleteInvoiceById(Long id) {
        return invoiceMap.remove(id);
    }

}