package pl.coderstrust.accounting.repositories.hibernate;

import org.springframework.data.repository.CrudRepository;
import pl.coderstrust.accounting.model.hibernate.InvoiceHib;

public interface HibernateRepository extends CrudRepository<InvoiceHib, Long> { }
