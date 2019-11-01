package pl.coderstrust.accounting.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class RepositoryConfigurationTest {

    @Test
    void inMemoryDatabase() {
        RepositoryConfiguration repositoryConfiguration = new RepositoryConfiguration();
        assertNotNull(repositoryConfiguration.inMemoryDatabase());
    }
}