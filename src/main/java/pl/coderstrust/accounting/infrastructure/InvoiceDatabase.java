package pl.coderstrust.accounting.infrastructure;

import java.util.List;
import pl.coderstrust.accounting.model.Invoice;

public interface InvoiceDatabase {

    Invoice save(Invoice invoice);

    Invoice findById(Long id);

    List<Invoice> findAll();

    Invoice deleteById(Long id);

}
