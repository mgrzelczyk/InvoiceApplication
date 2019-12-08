package pl.coderstrust.accounting.infrastructure;

import java.util.List;
import pl.coderstrust.accounting.model.Invoice;

public interface InvoiceDatabase {

    Invoice saveInvoice(Invoice invoice);

    Invoice findInvoiceById(Long id);

    List<Invoice> findAllInvoices();

    Invoice deleteInvoiceById(Long id);

}
