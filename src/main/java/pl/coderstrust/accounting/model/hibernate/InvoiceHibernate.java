package pl.coderstrust.accounting.model.hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Entity
public class InvoiceHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @OneToOne(cascade = CascadeType.ALL)
    private CompanyHibernate buyer;
    @OneToOne(cascade = CascadeType.ALL)
    private CompanyHibernate seller;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<InvoiceEntryHibernate> entries;

    public InvoiceHibernate() {
    }

    public InvoiceHibernate(InvoiceHibernate invoiceHibernate) {
        setId(invoiceHibernate.getId());
        setDate(invoiceHibernate.getDate());
        setBuyer(invoiceHibernate.getBuyer());
        setSeller(invoiceHibernate.getSeller());
        setEntries(invoiceHibernate.getEntries());
    }

    public InvoiceHibernate(LocalDate date, CompanyHibernate buyer, CompanyHibernate seller,
                            List<InvoiceEntryHibernate> entries) {
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
        this.entries = entries;
    }

    public InvoiceHibernate(Long id, LocalDate date, CompanyHibernate buyer,
                            CompanyHibernate seller, List<InvoiceEntryHibernate> entries) {
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

    public CompanyHibernate getBuyer() {
        return buyer;
    }

    public void setBuyer(CompanyHibernate buyer) {
        this.buyer = buyer;
    }

    public CompanyHibernate getSeller() {
        return seller;
    }

    public void setSeller(CompanyHibernate seller) {
        this.seller = seller;
    }

    public List<InvoiceEntryHibernate> getEntries() {
        return entries;
    }

    public void setEntries(List<InvoiceEntryHibernate> entries) {
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
        InvoiceHibernate that = (InvoiceHibernate) obj;
        return Objects.equals(id, that.id)
                && Objects.equals(date, that.date)
                && Objects.equals(buyer, that.buyer)
                && Objects.equals(seller, that.seller)
                && Objects.equals(entries, that.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, buyer, seller, entries);
    }

    @Override
    public String toString() {
        return "InvoiceHibernate{"
                + "id=" + id
                + ", date=" + date
                + ", buyer=" + buyer
                + ", seller=" + seller
                + ", entries=" + entries
                + '}';
    }
}
