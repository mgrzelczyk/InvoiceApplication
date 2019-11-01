package pl.coderstrust.accounting.repositories;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import pl.coderstrust.accounting.infrastructure.Database;
import pl.coderstrust.accounting.model.Invoice;


public class InMemoryDatabase implements Database {

    private AtomicLong counter = new AtomicLong(0);
    public Map<Long, Invoice> invoiceMap = new ConcurrentHashMap<>();
    Invoice invoice = new Invoice();

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceMap.put(counter.incrementAndGet(), invoice);
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return invoiceMap.get(id);
    }

    @Override
    public List<Invoice> findAllnvoices() {
        Collection<Invoice> values = invoiceMap.values();
        return new ArrayList<>(values);
    }

    @Override
    public Invoice deleteByInvoice(Long id) {
        return invoiceMap.remove(id);
    }

}
