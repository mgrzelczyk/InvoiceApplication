package pl.coderstrust.accounting.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.repositories.hibernate.HibernateRepository;

public class HibernateDatabase implements InvoiceDatabase {

    @Autowired
    private HibernateRepository hibernateRepository;

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return hibernateRepository.save(invoice);
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        Optional<Invoice> invoice = hibernateRepository.findById(id);
        return invoice.orElse(null);
    }

    @Override
    public List<Invoice> findAllInvoices() {
        Iterable<Invoice> invoices = hibernateRepository.findAll();
        return StreamSupport.stream(invoices.spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Invoice deleteInvoiceById(Long id) {
        Optional<Invoice> invoice = hibernateRepository.findById(id);
        if(invoice.isPresent()){
            hibernateRepository.deleteById(id);
            return invoice.get();
        }
        return null;
    }

}
