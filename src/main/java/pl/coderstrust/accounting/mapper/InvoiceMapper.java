package pl.coderstrust.accounting.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.hibernate.InvoiceHib;

@Mapper
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    Invoice toInvoice(InvoiceHib invoiceHib);

    InvoiceHib toInvoiceHib(Invoice invoice);

    List<Invoice> toInvoices(List<InvoiceHib> invoiceHib);
}
