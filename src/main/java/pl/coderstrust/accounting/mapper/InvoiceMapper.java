package pl.coderstrust.accounting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.hibernate.InvoiceHibernate;

@Mapper
public interface InvoiceMapper {

    @Mappings({
        @Mapping(target = "id", source = "invoiceHibernate.id"),
        @Mapping(target = "date", source = "invoiceHibernate.date",
            dateFormat = "yyyy-MM-dd"),
        @Mapping(target = "buyer", source = "invoiceHibernate.buyer"),
        @Mapping(target = "seller", source = "invoiceHibernate.seller"),
        @Mapping(target = "entries", source = "invoiceHibernate.entries")})
    Invoice toInvoice(InvoiceHibernate invoiceHibernate);

    @Mappings({
        @Mapping(target = "id", source = "invoice.id"),
        @Mapping(target = "date", source = "invoice.date",
            dateFormat = "yyyy-MM-dd"),
        @Mapping(target = "buyer", source = "invoice.buyer"),
        @Mapping(target = "seller", source = "invoice.seller"),
        @Mapping(target = "entries", source = "invoice.entries")})
    InvoiceHibernate toInvoiceHib(Invoice invoice);

}
