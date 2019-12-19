package pl.coderstrust.accounting.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.mapper.InvoiceMapper;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.hibernate.InvoiceHib;
import pl.coderstrust.accounting.repositories.hibernate.HibernateRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HibernateDatabase implements InvoiceDatabase {

    @Autowired
    private HibernateRepository hibernateRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        Invoice copiedInvoice = invoice;
        if (copiedInvoice.getId() == null && copiedInvoice.getDate() == null) {
            throw new NullPointerException();
        } else if (copiedInvoice.getId() != null && copiedInvoice.getDate() == null) {
            throw new NullPointerException();
        } else {
            InvoiceHib invoiceHib = invoiceMapper.toInvoiceHib(invoice);
            InvoiceHib saved = hibernateRepository.save(invoiceHib);
            return invoiceMapper.toInvoice(saved);
        }
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        Optional<InvoiceHib> object = hibernateRepository.findById(id);
        return object.map(invoiceMapper::toInvoice).orElse(null);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        Iterable<InvoiceHib> invoices = hibernateRepository.findAll();
        return StreamSupport
                .stream(invoices.spliterator(), false)
                .map(invoiceMapper::toInvoice)
                .collect(Collectors.toList());
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        Optional<InvoiceHib> object = hibernateRepository.findById(id);
        if (object.isPresent()) {
            hibernateRepository.deleteById(id);
            return invoiceMapper.toInvoice(object.get());
        }
        return null;
    }

}
