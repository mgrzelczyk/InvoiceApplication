import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import pl.coderstrust.Application;

class SimpleTest {

    @Test
    void simpleTest() {
        Application.main(null);
        assertTrue(true);
    }
}
