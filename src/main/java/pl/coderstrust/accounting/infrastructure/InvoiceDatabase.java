package pl.coderstrust.accounting.infrastructure;

import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.List;

public interface InvoiceDatabase<T, ID> {

    Invoice saveInvoice(T invoice) throws IOException;

    Invoice findInvoiceById(ID id) throws IOException;

    List<Invoice> findAllInvoices() throws IOException;

    Invoice deleteInvoiceById(ID id) throws IOException;

}
