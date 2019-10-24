package pl.coderstrust.accounting.database;

import pl.coderstrust.accounting.infrastructure.Database;
import pl.coderstrust.accounting.model.Invoice;

import java.util.List;

public class InFileDatabase implements Database {

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return null;
    }

    @Override
    public Invoice findInvoiceById(Long id) {
        return null;
    }

    @Override
    public List<Invoice> findAllnvoices() {
        return null;
    }

    @Override
    public Invoice deleteByInvoice(Long id) {
        return null;
    }
}
