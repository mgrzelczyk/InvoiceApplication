package pl.coderstrust.accounting.infrastructure;

import java.io.IOException;
import java.util.List;
import pl.coderstrust.accounting.model.Invoice;

public interface InvoiceDatabase {

    Invoice saveInvoice(Invoice invoice);

    Invoice findInvoiceById(Long id) throws IOException;

    List<Invoice> findAllnvoices() throws IOException;

    Invoice deleteByInvoice(Long id) throws IOException;

}
