package pl.coderstrust.accounting.model;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceEntry {

    private Long id;
    private String description;
    private BigDecimal price;
    private int vatValue;
    private Vat vatRate;
    private final static Logger LOGGER = Logger.getLogger(InvoiceEntry.class);

    public InvoiceEntry() {
        LOGGER.info("Invoice entry created");
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
        LOGGER.info("Invoice get ID");
        return id;
    }

    public void setId(Long id) {
        LOGGER.info("Invoice set ID");
        this.id = id;
    }

    public String getDescription() {
        LOGGER.info("Invoice get description");
        return description;
    }

    public void setDescription(String description) {
        LOGGER.info("Invoice set description");
        this.description = description;
    }

    public BigDecimal getPrice() {
        LOGGER.info("Invoice get price");
        return price;
    }

    public void setPrice(BigDecimal price) {
        LOGGER.info("Invoice set price");
        this.price = price;
    }

    public int getVatValue() {
        LOGGER.info("Invoice get Vat value");
        return vatValue;
    }

    public void setVatValue(int vatValue) {
        LOGGER.info("Invoice set Vat value");
        this.vatValue = vatValue;
    }

    public Vat getVatRate() {
        LOGGER.info("Invoice get Vat rate");
        return vatRate;
    }

    public void setVatRate(Vat vatRate) {
        LOGGER.info("Invoice set Vat rate");
        this.vatRate = vatRate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InvoiceEntry that = (InvoiceEntry) obj;
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
}
