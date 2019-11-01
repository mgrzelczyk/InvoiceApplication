package pl.coderstrust.accounting;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.repositories.FileHelper;
import pl.coderstrust.accounting.services.InvoiceBook;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;

public class Application {

    private static Logger log = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws IOException {
        log.info("Hello World");
    }
}
