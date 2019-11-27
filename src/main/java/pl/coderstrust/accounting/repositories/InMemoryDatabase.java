package pl.coderstrust.accounting.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

public class InMemoryDatabase implements InvoiceDatabase<Invoice, Long> {

    private Map<Long, Invoice> invoiceMap = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

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
        throw new NullPointerException("There isn't option");
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
