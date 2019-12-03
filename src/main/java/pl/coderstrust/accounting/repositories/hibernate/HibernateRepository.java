package pl.coderstrust.accounting.repositories.hibernate;

import org.springframework.data.repository.CrudRepository;
import pl.coderstrust.accounting.model.Invoice;

public interface HibernateRepository extends CrudRepository<Invoice, Long> { }
