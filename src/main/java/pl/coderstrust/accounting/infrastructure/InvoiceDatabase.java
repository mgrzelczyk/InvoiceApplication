package pl.coderstrust.accounting.infrastructure;

import java.io.IOException;
import java.util.List;
import pl.coderstrust.accounting.model.Invoice;

public interface Database {

    Invoice saveInvoice(Invoice invoice);

    Invoice findInvoiceById(Long id) throws IOException;

    List<String> findAllnvoices();

    Invoice deleteByInvoice(Long id) throws IOException;

}
