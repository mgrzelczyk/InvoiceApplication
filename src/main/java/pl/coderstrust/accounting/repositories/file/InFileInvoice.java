package pl.coderstrust.accounting.repositories.file;

import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.time.LocalDate;
import java.util.List;

class InFileInvoice extends Invoice {

    private boolean deleted;

    InFileInvoice(){
    }

    InFileInvoice(boolean deleted) {
        this.deleted = deleted;
    }

    private InFileInvoice(Long id,
                          LocalDate date,
                          Company buyer,
                          Company seller,
                          List<InvoiceEntry> entries,
                          boolean deleted) {
        super(id, date, buyer, seller, entries);
        this.deleted = deleted;
    }

    InFileInvoice(Invoice invoice, boolean deleted) {
        this(invoice.getId(),
            invoice.getDate(),
            invoice.getBuyer(),
            invoice.getSeller(),
            invoice.getEntries(),
            deleted);
    }

    boolean isDeleted() {
        return deleted;
    }

    void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    boolean getDeleted(boolean deleted) {
        return deleted;
    }
}
