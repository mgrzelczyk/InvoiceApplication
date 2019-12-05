package pl.coderstrust.accounting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.repositories.FileHelper;
import pl.coderstrust.accounting.repositories.InFileDatabase;
import pl.coderstrust.accounting.repositories.InMemoryDatabase;

import java.io.IOException;

@Configuration
//@PropertySource(value = "application.properties", ignoreResourceNotFound = true)
public class AppConfiguration {

    @Value("${fileDatabase}")
    String fileDatabase;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    @ConditionalOnProperty(name = "database", havingValue = "in-memory")
    public InvoiceDatabase inMemoryDatabase() {
        return new InMemoryDatabase();
    }

    @Bean
    @ConditionalOnProperty(name = "database", havingValue = "in-file")
    public InvoiceDatabase inFileDatabase() throws IOException {
        FileHelper fileHelper = new FileHelper(fileDatabase);
        return new InFileDatabase(fileHelper, objectMapper);
    }

}
