package pl.coderstrust.accounting.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.services.InvoiceBook;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    private final InvoiceBook invoiceBook;
    private final static Logger log = LoggerFactory.getLogger(InvoiceController.class);

    public InvoiceController(InvoiceBook invoiceBook) {
        log.info("Invoice controller run");
        this.invoiceBook = invoiceBook;
    }

    @GetMapping("/invoices")
    public ResponseEntity findAllInvoices(
        @RequestParam(value = "from", required = false)
        @DateTimeFormat(iso = ISO.DATE)
            LocalDate from,
        @RequestParam(value = "to", required = false)
        @DateTimeFormat(iso = ISO.DATE)
            LocalDate to) {
        List<Invoice> invoices;
        if (from != null && to != null) {
            invoices = invoiceBook.findAllInvoiceByDateRange(from, to);
        } else {
            invoices = invoiceBook.findAllInvoices();
        }
        if (invoices != null) {
            return ResponseEntity.ok().body(invoices);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    @PostMapping("/invoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice createdInvoice = invoiceBook.saveInvoice(invoice);
        if (createdInvoice == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdInvoice.getId())
            .toUri();
        return ResponseEntity.created(uri).body(createdInvoice);
    }

    @PutMapping("/invoice")
    public ResponseEntity<Invoice> editInvoice(@RequestBody Invoice invoice) {
        if (invoiceBook.findInvoiceById(invoice.getId()) != null) {
            Invoice editedInvoice = invoiceBook.saveInvoice(invoice);
            if (editedInvoice != null) {
                return ResponseEntity.ok().body(editedInvoice);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/invoice/{id}")
    public ResponseEntity<Object> deleteInvoiceById(@PathVariable("id") Long id) {
        Invoice deletedInvoice = invoiceBook.deleteInvoiceById(id);
        if (deletedInvoice == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
