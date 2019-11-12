package pl.coderstrust.accounting.controllers;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.services.InvoiceBook;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    private InvoiceBook invoiceBook;

    public InvoiceController(InvoiceBook invoiceBook) {
        this.invoiceBook = invoiceBook;
    }

    @GetMapping("/invoices/")
    public ResponseEntity<List<Invoice>> findAllInvoices() {
        List<Invoice> invoices = invoiceBook.findAllInvoices();
        if (invoices == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(invoices);
        }
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<Invoice> findInvoiceById(@PathVariable("id") Long id) {
        Invoice foundInvoice = invoiceBook.findInvoiceById(id);
        if (foundInvoice == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(foundInvoice);
        }
    }

    @PostMapping("/invoice/")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice createdInvoice = invoiceBook.saveInvoice(invoice);
        if (createdInvoice == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdInvoice.getId())
                .toUri();
            return ResponseEntity.created(uri).body(createdInvoice);
        }
    }

    @PutMapping("/invoice/")
    public ResponseEntity<Invoice> editInvoice(@RequestBody Invoice invoice) {
        Invoice editedInvoice = invoiceBook.saveInvoice(invoice);
        if (editedInvoice == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(editedInvoice);
        }
    }

    @DeleteMapping("/invoice/{id}")
    public ResponseEntity<Object> deleteInvoiceById(@PathVariable("id") Long id) {
        Invoice deletedInvoice = invoiceBook.deleteInvoiceById(id);
        if (deletedInvoice == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
