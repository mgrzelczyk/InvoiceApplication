package pl.coderstrust.accounting.model.hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "Invoice")
public class InvoiceHib {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @ManyToOne(cascade = CascadeType.ALL)
    private CompanyHib buyer;
    @ManyToOne(cascade = CascadeType.ALL)
    private CompanyHib seller;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<InvoiceEntryHib> entries;

    public InvoiceHib() {
    }

    public InvoiceHib(InvoiceHib invoiceHib) {
        setId(invoiceHib.getId());
        setDate(invoiceHib.getDate());
        setBuyer(invoiceHib.getBuyer());
        setSeller(invoiceHib.getSeller());
        setEntries(invoiceHib.getEntries());
    }

    public InvoiceHib(Long id, LocalDate date, CompanyHib buyer,
        CompanyHib seller, List<InvoiceEntryHib> entries) {
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

    public CompanyHib getBuyer() {
        return buyer;
    }

    public void setBuyer(CompanyHib buyer) {
        this.buyer = buyer;
    }

    public CompanyHib getSeller() {
        return seller;
    }

    public void setSeller(CompanyHib seller) {
        this.seller = seller;
    }

    public List<InvoiceEntryHib> getEntries() {
        return entries;
    }

    public void setEntries(List<InvoiceEntryHib> entries) {
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
        InvoiceHib invoiceHib = (InvoiceHib) obj;
        return Objects.equals(id, invoiceHib.id)
            && Objects.equals(date, invoiceHib.date)
            && Objects.equals(buyer, invoiceHib.buyer)
            && Objects.equals(seller, invoiceHib.seller)
            && Objects.equals(entries, invoiceHib.entries);
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
