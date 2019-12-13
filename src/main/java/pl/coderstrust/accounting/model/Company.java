package pl.coderstrust.accounting.model;

import org.apache.log4j.Logger;

import java.util.Objects;

public class Company {

    private Long id;
    private String tin;
    private String address;
    private String name;
    private final static Logger LOGGER = Logger.getLogger(Company.class);

    public Company() {
        LOGGER.info("Company created");
    }

    public Company(Long id, String tin, String address, String name) {
        this.id = id;
        this.tin = tin;
        this.address = address;
        this.name = name;
    }

    public Long getId() {
        LOGGER.info("Company get ID");
        return id;
    }

    public void setId(Long id) {
        LOGGER.info("Company set ID");
        this.id = id;
    }

    public String getTin() {
        LOGGER.info("Company get tin");
        return tin;
    }

    public void setTin(String tin) {
        LOGGER.info("Company set tin");
        this.tin = tin;
    }

    public String getAddress() {
        LOGGER.info("Company get address");
        return address;
    }

    public void setAddress(String address) {
        LOGGER.info("Company set address");
        this.address = address;
    }

    public String getName() {
        LOGGER.info("Company get name");
        return name;
    }

    public void setName(String name) {
        LOGGER.info("Company set name");
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Company company = (Company) obj;
        return Objects.equals(id, company.id)
            && Objects.equals(tin, company.tin)
            && Objects.equals(address, company.address)
            && Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tin, address, name);
    }

    @Override
    public String toString() {
        return "Company{"
            + "id=" + id
            + ", tin='" + tin + '\''
            + ", address='" + address + '\''
            + ", name='" + name + '\''
            + '}';
    }
}
