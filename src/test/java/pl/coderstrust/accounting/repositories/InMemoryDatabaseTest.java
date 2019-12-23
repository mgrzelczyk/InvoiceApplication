package pl.coderstrust.accounting.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.repositories.inMemory.InMemoryDatabase;

@ActiveProfiles("memory")
class InMemoryDatabaseTest extends DatabaseTests {

    @Autowired
    private InMemoryDatabase inMemoryDatabase;

    @Override
    InvoiceDatabase getDatabase() {
        return inMemoryDatabase;
    }

}
