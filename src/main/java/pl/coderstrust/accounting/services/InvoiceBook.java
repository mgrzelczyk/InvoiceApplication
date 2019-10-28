package pl.coderstrust.accounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.repositories.InMemoryDatabase;

@Service
public class InvoiceBook {

    private InMemoryDatabase inMemoryDatabase;

    public InvoiceBook(InMemoryDatabase inMemoryDatabase) {
        this.inMemoryDatabase = inMemoryDatabase;
    }

}
