package pl.coderstrust.accounting.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {

    private Long id;
    private LocalDate date;
    private Company buyer;
    private Company seller;
    private List<InvoiceEntry> entries;

    public Invoice() {
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
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Company getBuyer() {
        return buyer;
    }

    public void setBuyer(Company buyer) {
        this.buyer = buyer;
    }

    public Company getSeller() {
        return seller;
    }

    public void setSeller(Company seller) {
        this.seller = seller;
    }

    public List<InvoiceEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<InvoiceEntry> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) object;
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