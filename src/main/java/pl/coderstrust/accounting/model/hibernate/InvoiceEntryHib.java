package pl.coderstrust.accounting.model.hibernate;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class InvoiceEntryHib {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal price;
    private int vatValue;
    private VatHib vatHibRate;

    public InvoiceEntryHib() {
    }

    public InvoiceEntryHib(Long id, String description, BigDecimal price, int vatValue,
        VatHib vatHibRate) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.vatValue = vatValue;
        this.vatHibRate = vatHibRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getVatValue() {
        return vatValue;
    }

    public void setVatValue(int vatValue) {
        this.vatValue = vatValue;
    }

    public VatHib getVatHibRate() {
        return vatHibRate;
    }

    public void setVatHibRate(VatHib vatHibRate) {
        this.vatHibRate = vatHibRate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InvoiceEntryHib that = (InvoiceEntryHib) obj;
        return vatValue == that.vatValue
            && Objects.equals(id, that.id)
            && Objects.equals(description, that.description)
            && Objects.equals(price, that.price)
            && vatHibRate == that.vatHibRate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, vatValue, vatHibRate);
    }

    @Override
    public String toString() {
        return "InvoiceEntryHib{"
            + "id=" + id
            + ", description='" + description + '\''
            + ", price=" + price
            + ", vatValue=" + vatValue
            + ", vatHibRate=" + vatHibRate
            + '}';
    }
}
