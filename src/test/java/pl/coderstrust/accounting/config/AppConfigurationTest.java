package pl.coderstrust.accounting.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AppConfigurationTest {

    @Test
    void inMemoryDatabase() {
        AppConfiguration appConfiguration = new AppConfiguration();
        assertNotNull(appConfiguration.inMemoryDatabase());
    }
}
