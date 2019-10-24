package pl.coderstrust.accounting.infrastructure;

import java.util.List;
import pl.coderstrust.accounting.model.Invoice;

public interface Database {

    Invoice saveInvoice(Invoice invoice);

    Invoice findInvoiceById(Long id);

    List<Invoice> findAllnvoices();

    Invoice deleteByInvoice(Long id);

}
