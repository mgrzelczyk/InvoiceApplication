package pl.coderstrust.accounting.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.coderstrust.accounting.repositories.InMemoryDatabase;

@Configuration
public class RepositoryConfiguration {

    @Bean
    @ConditionalOnProperty(
        prefix = "repository.enable",
        name = "filedatabase",
        havingValue = "true")
    public InMemoryDatabase inMemoryDatabase() {
        return new InMemoryDatabase();
    }

}
