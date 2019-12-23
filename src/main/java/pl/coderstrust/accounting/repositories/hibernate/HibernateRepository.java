package pl.coderstrust.accounting.repositories.hibernate;

import org.springframework.data.repository.CrudRepository;
import pl.coderstrust.accounting.model.hibernate.InvoiceHibernate;

public interface HibernateRepository extends CrudRepository<InvoiceHibernate, Long> {

}
