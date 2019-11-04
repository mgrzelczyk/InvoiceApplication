package pl.coderstrust.accounting.repositories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

public class InMemoryDatabase implements InvoiceDatabase {

    private Map<Long, Invoice> invoiceMap = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceMap.put(counter.incrementAndGet(), invoice);
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return invoiceMap.get(id);
    }

    @Override
    public Map<Long, Invoice> findAllInvoices() {
        return invoiceMap;
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        return invoiceMap.remove(id);
    }

}