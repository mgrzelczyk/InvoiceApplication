package pl.coderstrust.accounting.smokeTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.coderstrust.accounting.controllers.InvoiceController;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SmokeTest {

    @Autowired
    private InvoiceController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
