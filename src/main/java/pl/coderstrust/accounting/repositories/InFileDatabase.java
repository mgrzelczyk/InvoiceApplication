package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

public class InFileDatabase implements InvoiceDatabase {

    private final AtomicLong counter;
    private final FileHelper fileHelper;
    private final ObjectMapper objectMapper;
    private final static Logger log = LoggerFactory.getLogger(InFileDatabase.class);

    public InFileDatabase(FileHelper fileHelper, ObjectMapper objectMapper) throws IOException {
        this.fileHelper = fileHelper;
        this.objectMapper = objectMapper;
        this.counter = new AtomicLong(getLastId());
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) throws IOException {
        if (invoice.getId() == null) {
            log.info("Save invoice as insert");
            return insertInvoice(invoice);
        } else {
            log.info("Save invoice as update");
            return updateInvoice(invoice);
        }
    }

    @Override
    public Invoice findInvoiceById(Long id) throws IOException {
        if (id == null) {
            log.info("Id is null, cannot find invoice");
            throw new IllegalArgumentException("ID is null.");
        }
        log.info("Finded invoice with ID");
        return loadInvoices().get(id);
    }

    @Override
    public List<Invoice> findAllInvoices() throws IOException {
        log.info("Find all invoices");
        return getInvoices();
    }

    @Override
    public Invoice deleteInvoiceById(Long id) throws IOException {
        if (id == null) {
            log.info("Cannot delete invoice with null ID");
            throw new IllegalArgumentException("ID is null.");
        }
        Invoice invoice = findInvoiceById(id);
        InFileInvoice deletedInvoice = createInFileInvoice(invoice, true);
        String json = objectMapper.writeValueAsString(deletedInvoice);
        fileHelper.writeLineToFile(json);
        log.info("Delete invoice by ID");
        return invoice;
    }

    private Long getLastId() throws IOException {
        try {
            log.info("Get last ID");
            return Collections.max(loadInvoices().keySet());
        } catch (NoSuchElementException e) {
            log.info("No such element, last ID equal zero");
            return 0L;
        }
    }

    private ArrayList<InFileInvoice> readInvoicesFromFile() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            inFileInvoices.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
        }
        log.info("Read invoices from file");
        return inFileInvoices;
    }

    private Map<Long, InFileInvoice> loadInvoices() throws IOException {
        List<InFileInvoice> inFileInvoices = readInvoicesFromFile();
        Map<Long, InFileInvoice> database = new HashMap<>();
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        log.info("Invoice loaded");
        return database;
    }

    private Invoice insertInvoice(Invoice invoice) throws IOException {
        Long lastId = getLastId();
        InFileInvoice inFileInvoice = createInFileInvoice(invoice, false);
        inFileInvoice.setId(lastId + 1L);
        String inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(inFilenvoiceJson);
        counter.incrementAndGet();
        log.info("Insert invoice in File Database");
        return createInvoice(inFileInvoice);
    }

    private Invoice createInvoice(InFileInvoice inFileInvoice) {
        log.info("Create new Invoice");
        return new Invoice();
    }

    private Invoice updateInvoice(Invoice invoice) throws IOException {
        InFileInvoice inFileInvoice = createInFileInvoice(invoice, false);
        String json = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(json);
        log.info("Update invoice");
        return invoice;
    }

    private InFileInvoice createInFileInvoice(Invoice invoice, boolean deleted) throws IOException {
        InFileInvoice inFileInvoice = new InFileInvoice(invoice, true);
        String inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(inFilenvoiceJson);
        log.info("Create in File Invoice");
        return inFileInvoice;
    }

    private List<Invoice> getInvoices() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        List<Invoice> stringsConvertedToList = new ArrayList<>();

        for (String string : strings) {
            stringsConvertedToList.add(objectMapper.readValue(string, Invoice.class));
        }
        log.info("Get Invoices");
        return stringsConvertedToList;
    }

}
