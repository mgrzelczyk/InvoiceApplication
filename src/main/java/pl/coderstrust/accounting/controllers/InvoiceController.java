package pl.coderstrust.accounting.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class InvoiceController {

    private InvoiceDatabase invoiceDatabase;

    public InvoiceController(InvoiceDatabase invoiceDatabase) {
        this.invoiceDatabase = invoiceDatabase;
    }

    @RequestMapping(value = "/invoice/", method = RequestMethod.GET)
    public ResponseEntity<Invoice> findAllInvoices(){
        return null;
    }

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Invoice>> findInvoiceById(@PathVariable("id") Long id){
        return null;
    }

    @RequestMapping(value = "/invoice/", method = RequestMethod.POST)
    public ResponseEntity<Void> createInvoice(@RequestBody Invoice invoice){
        return null;
    }

    @RequestMapping(value = "/invoice/", method = RequestMethod.PUT)
    public ResponseEntity<Invoice> editInvoiceById(@RequestBody Invoice invoice){
        return null;
    }

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Invoice> deleteInvoiceById(@PathVariable("id") Long id){
        return null;
    }

}
