package pl.coderstrust.accounting.model.hibernate;

public enum VatHibernate {
    STANDARD_23(23),
    REDUCED_8(8),
    REDUCED_7(7),
    REDUCED_5(5),
    REDUCED_4(4),
    REDUCED_0(0),
    TAX_FREE(0);

    private final int value;

    VatHibernate(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
