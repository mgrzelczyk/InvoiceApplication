package pl.coderstrust.accounting.model;

import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {

    private Long id;
    private LocalDate date;
    private Company buyer;
    private Company seller;
    private List<InvoiceEntry> entries;
    private final static Logger LOGGER = Logger.getLogger(Invoice.class);

    public Invoice() {
        LOGGER.info("Invoice created");
    }

    public Invoice(Invoice invoice) {
        setId(invoice.getId());
        setDate(invoice.getDate());
        setBuyer(invoice.getBuyer());
        setSeller(invoice.getSeller());
        setEntries(invoice.getEntries());
    }

    public Invoice(Long id, LocalDate date, Company buyer,
        Company seller, List<InvoiceEntry> entries) {
        this.id = id;
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
        this.entries = entries;
    }

    public Long getId() {
        LOGGER.info("Invoice get ID");
        return id;
    }

    public void setId(Long id) {
        LOGGER.info("Invoice set ID");
        this.id = id;
    }

    public LocalDate getDate() {
        LOGGER.info("Invoice get date");
        return date;
    }

    public void setDate(LocalDate date) {
        LOGGER.info("Invoice set date");
        this.date = date;
    }

    public Company getBuyer() {
        LOGGER.info("Invoice get buyer");
        return buyer;
    }

    public void setBuyer(Company buyer) {
        LOGGER.info("Invoice set buyer");
        this.buyer = buyer;
    }

    public Company getSeller() {
        LOGGER.info("Invoice get seller");
        return seller;
    }

    public void setSeller(Company seller) {
        LOGGER.info("Invoice get buyer");
        this.seller = seller;
    }

    public List<InvoiceEntry> getEntries() {
        LOGGER.info("Invoice get entries");
        return entries;
    }

    public void setEntries(List<InvoiceEntry> entries) {
        LOGGER.info("Invoice set entries");
        this.entries = entries;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) obj;
        return Objects.equals(id, invoice.id)
            && Objects.equals(date, invoice.date)
            && Objects.equals(buyer, invoice.buyer)
            && Objects.equals(seller, invoice.seller)
            && Objects.equals(entries, invoice.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, buyer, seller, entries);
    }

    @Override
    public String toString() {
        return "Invoice{"
            + "id=" + id
            + ", date=" + date
            + ", buyer=" + buyer
            + ", seller=" + seller
            + ", entries=" + entries
            + '}';
    }
}
