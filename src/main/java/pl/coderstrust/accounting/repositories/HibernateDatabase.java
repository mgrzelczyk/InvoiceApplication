package pl.coderstrust.accounting.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.hibernate.InvoiceHib;
import pl.coderstrust.accounting.repositories.hibernate.HibernateRepository;
import pl.coderstrust.accounting.mapper.InvoiceMapper;

public class HibernateDatabase implements InvoiceDatabase {

    @Autowired
    private HibernateRepository hibernateRepository;

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        if(invoice != null) {
            InvoiceHib invoiceHib = InvoiceMapper.INSTANCE.toInvoiceHib(invoice);
            InvoiceHib saved = hibernateRepository.save(invoiceHib);
            return InvoiceMapper.INSTANCE.toInvoice(saved);
        }
        throw new NullPointerException("There isn't option");
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        Optional<InvoiceHib> object = hibernateRepository.findById(id);
        return object.map(InvoiceMapper.INSTANCE::toInvoice).orElse(null);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        Iterable<InvoiceHib> invoices = hibernateRepository.findAll();
        List<InvoiceHib> collect = StreamSupport.stream(invoices.spliterator(), false)
            .collect(Collectors.toList());
        return InvoiceMapper.INSTANCE.toInvoices(collect);
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        Optional<InvoiceHib> object = hibernateRepository.findById(id);
        if(object.isPresent()){
            hibernateRepository.deleteById(id);
            return InvoiceMapper.INSTANCE.toInvoice(object.get());
        }
        return null;
    }

}
