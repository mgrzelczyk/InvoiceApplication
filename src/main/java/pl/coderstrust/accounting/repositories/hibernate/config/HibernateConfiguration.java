package pl.coderstrust.accounting.repositories.hibernate.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.repositories.hibernate.HibernateDatabase;

@Configuration
public class HibernateConfiguration {

    @Bean
    @ConditionalOnProperty(name = "database", havingValue = "in-hibernate")
    public InvoiceDatabase inHibernateDatabase() {
        return new HibernateDatabase();
    }

}
