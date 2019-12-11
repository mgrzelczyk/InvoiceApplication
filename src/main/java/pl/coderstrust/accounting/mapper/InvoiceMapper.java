package pl.coderstrust.accounting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.hibernate.InvoiceHib;

@Mapper()
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    @Mappings({
        @Mapping(target = "id", source = "invoiceHib.id"),
        @Mapping(target = "date", source = "invoiceHib.date", dateFormat = "yyyy-MM-dd"),
        @Mapping(target = "buyer", source = "invoiceHib.buyer"),
        @Mapping(target = "seller", source = "invoiceHib.seller"),
        @Mapping(target = "entries", source = "invoiceHib.entries")
    })
    Invoice toInvoice(InvoiceHib invoiceHib);

    @Mappings({
        @Mapping(target = "id", source = "invoice.id"),
        @Mapping(target = "date", source = "invoice.date", dateFormat = "yyyy-MM-dd"),
        @Mapping(target = "buyer", source = "invoice.buyer"),
        @Mapping(target = "seller", source = "invoice.seller"),
        @Mapping(target = "entries", source = "invoice.entries")
    })
    InvoiceHib toInvoiceHib(Invoice invoice);

}
