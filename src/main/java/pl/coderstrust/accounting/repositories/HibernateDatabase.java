package pl.coderstrust.accounting.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger log = LoggerFactory.getLogger(HibernateDatabase.class);

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        Invoice copiedInvoice = invoice;
        if (copiedInvoice.getId() == null && copiedInvoice.getDate() == null) {
            log.warn("save - Empty invoice");
            throw new NullPointerException();
        } else if (copiedInvoice.getId() != null && copiedInvoice.getDate() == null) {
            log.warn("save - Bad invoice fields");
            throw new NullPointerException();
        } else {
            InvoiceHib invoiceHib = invoiceMapper.toInvoiceHib(invoice);
            InvoiceHib saved = hibernateRepository.save(invoiceHib);
            log.info("save - Invoice saved");
            return invoiceMapper.toInvoice(saved);
        }
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        Optional<InvoiceHib> object = hibernateRepository.findById(id);
        log.info("Find invoice by id");
        return object.map(invoiceMapper::toInvoice).orElse(null);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        Iterable<InvoiceHib> invoices = hibernateRepository.findAll();
        log.info("Find all invoices");
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
            log.info("delete - Invoice deleted");
            return invoiceMapper.toInvoice(object.get());
        }
        log.warn("delete - Invoice doesn't exist");
        return null;
    }

}
