package pl.coderstrust.accounting.repositories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

@Repository
public class InMemoryDatabase implements InvoiceDatabase {

    private Map<Long, Invoice> invoiceMap = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        Long id = counter.incrementAndGet();
        return invoiceMap.put(id, invoice);
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return invoiceMap.get(id);
    }

    @Override
    public Map<Long, Invoice> findAllnvoices() {
        return invoiceMap;
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        return invoiceMap.remove(id);
    }

}