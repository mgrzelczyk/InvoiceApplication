package pl.coderstrust.accounting;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    @Test
    void main() {
        Application.main(null);
        new Application();
        assertTrue(true);
    }
}