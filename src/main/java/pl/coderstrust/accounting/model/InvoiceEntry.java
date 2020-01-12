package pl.coderstrust.accounting.model;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceEntry {

    private Long id;
    private String description;
    private BigDecimal price;
    private int vatValue;
    private Vat vatRate;

    public InvoiceEntry() {
    }

    public InvoiceEntry(Long id, String description, BigDecimal price, int vatValue,
                        Vat vatRate) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.vatValue = vatValue;
        this.vatRate = vatRate;
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

    public Vat getVatRate() {
        return vatRate;
    }

    public void setVatRate(Vat vatRate) {
        this.vatRate = vatRate;
    }

    @Override
    public String toString() {
        return "InvoiceEntry{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", price=" + price
                + ", vatValue=" + vatValue
                + ", vatRate=" + vatRate
                + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        InvoiceEntry that = (InvoiceEntry) object;
        return vatValue == that.vatValue
            && Objects.equals(id, that.id)
            && Objects.equals(description, that.description)
            && Objects.equals(price, that.price)
            && vatRate == that.vatRate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, vatValue, vatRate);
    }
}