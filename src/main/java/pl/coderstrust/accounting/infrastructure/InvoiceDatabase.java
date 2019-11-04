package pl.coderstrust.accounting.infrastructure;

import java.util.Map;
import pl.coderstrust.accounting.model.Invoice;

public interface InvoiceDatabase {

    Invoice saveInvoice(Invoice invoice);

    Invoice findInvoiceById(Long id);

    Map<Long, Invoice> findAllInvoices();

    Invoice deleteInvoiceById(Long id);

}
