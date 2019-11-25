package pl.coderstrust.accounting.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

public class InMemoryDatabase implements InvoiceDatabase {

    private Map<Long, Invoice> invoiceMap = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    @Override //zrobic kopie
    public Invoice saveInvoice(Invoice invoice) {
        if (invoice.getId() == null) {
            Long id = counter.incrementAndGet();
            invoiceMap.put(id, invoice);
            invoice.setId(id);
            return invoice;
        } else if (invoiceMap.containsKey(invoice.getId())) {
            invoiceMap.put(invoice.getId(), invoice);
            return invoice;
        }
        throw new NullPointerException("it doesn't work :C");
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
