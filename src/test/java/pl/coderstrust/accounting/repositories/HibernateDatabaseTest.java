package pl.coderstrust.accounting.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;

@ActiveProfiles("hibernate")
class HibernateDatabaseTest extends DatabaseTests {

    @Autowired
    private HibernateDatabase hibernateDatabase;

    @Override
    InvoiceDatabase getDatabase() {
        return hibernateDatabase;
    }
}
