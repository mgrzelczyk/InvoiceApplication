package pl.coderstrust.accounting.repositories.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.mapper.InvoiceMapper;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.hibernate.InvoiceHibernate;
import pl.coderstrust.accounting.repositories.hibernate.repository.HibernateRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HibernateDatabase implements InvoiceDatabase {

    @Autowired
    private HibernateRepository hibernateRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    private final Logger log = LoggerFactory.getLogger(HibernateDatabase.class);

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        Invoice copiedInvoice = invoice;
        if (copiedInvoice.getId() == null && copiedInvoice.getDate() == null) {
            log.warn("[save] Empty invoice");
            throw new NullPointerException();
        } else if (copiedInvoice.getId() != null && copiedInvoice.getDate() == null) {
            log.warn("[save] Invalid invoice fields");
            throw new NullPointerException();
        } else {
            InvoiceHibernate invoiceHibernate = invoiceMapper.toInvoiceHib(invoice);
            InvoiceHibernate saved = hibernateRepository.save(invoiceHibernate);
            return invoiceMapper.toInvoice(saved);
        }
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        Optional<InvoiceHibernate> object = hibernateRepository.findById(id);
        return object.map(invoiceMapper::toInvoice).orElse(null);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        Iterable<InvoiceHibernate> invoices = hibernateRepository.findAll();
        return StreamSupport
                .stream(invoices.spliterator(), false)
                .map(invoiceMapper::toInvoice)
                .collect(Collectors.toList());
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        Optional<InvoiceHibernate> object = hibernateRepository.findById(id);
        if (object.isPresent()) {
            hibernateRepository.deleteById(id);
            return invoiceMapper.toInvoice(object.get());
        }
        return null;
    }

}
