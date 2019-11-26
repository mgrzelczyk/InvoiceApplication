package pl.coderstrust.accounting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr353.JSR353Module;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.repositories.InMemoryDatabase;

@Configuration
public class AppConfiguration {

    @Bean
    @ConditionalOnProperty(name = "database", havingValue = "in-memory")
    public InvoiceDatabase inMemoryDatabase() {
        return new InMemoryDatabase();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
            .registerModule(new JSR353Module());
    }

}
