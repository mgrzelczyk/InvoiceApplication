package pl.coderstrust.accounting.infrastructure;

import pl.coderstrust.accounting.model.Invoice;

import java.util.List;

public interface InvoiceDatabase {

    Invoice saveInvoice(Invoice invoice);

    Invoice findInvoiceById(Long id);

    List<Invoice> findAllInvoices();

    Invoice deleteInvoiceById(Long id);

}
