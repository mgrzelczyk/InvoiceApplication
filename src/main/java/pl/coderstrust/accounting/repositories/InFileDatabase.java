package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final AtomicLong counter = new AtomicLong(getLastId());
    private final FileHelper fileHelper;
    private final ObjectMapper objectMapper;


    public InFileDatabase(FileHelper fileHelper, ObjectMapper objectMapper) throws IOException {
        this.fileHelper = fileHelper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) throws IOException {
        if (invoice.getId() == null) {
            return insertInvoice(invoice);
        } else {
            return updateInvoice(invoice);
        }
    }

    private Invoice updateInvoice(Invoice invoice) throws IOException {
        InFileInvoice inFileInvoice = createInFileInvoice(invoice, false);
        String json = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(json);
        return invoice;
    }

    @Override
    public Invoice findInvoiceById(Long id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        }
        return loadInvoices().get(id);
    }

    @Override
    public List<Invoice> findAllInvoices() throws IOException {
        return getInvoices();
    }

    @Override
    public Invoice deleteInvoiceById(Long id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        }

        Invoice invoice = findInvoiceById(id);
        InFileInvoice deletedInvoice = createInFileInvoice(invoice, true);
        String json = objectMapper.writeValueAsString(deletedInvoice);
        fileHelper.writeLineToFile(json);

        return invoice;
    }

    private Long getLastId() throws IOException {
        try {
            return Collections.max(loadInvoices().keySet());
        } catch (NoSuchElementException e) {
            return 0L;
        }
    }

    private Map<Long, InFileInvoice> loadInvoices() throws IOException {
        List<InFileInvoice> inFileInvoices = readInvoicesFromFile();
        Map<Long, InFileInvoice> database = new HashMap<>();
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        return database;
    }

    private Invoice insertInvoice(Invoice invoice) throws IOException {
        Long lastId = getLastId();
        InFileInvoice inFileInvoice = createInFileInvoice(invoice, false);
        inFileInvoice.setId(lastId + 1L);
        String inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(inFilenvoiceJson);
        counter.incrementAndGet();
        return createInvoice(inFileInvoice);
    }

    private Invoice createInvoice(InFileInvoice inFileInvoice) {
        new Invoice();
    }

    private List<InFileInvoice> readInvoicesFromFile() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            inFileInvoices.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
        }
        return inFileInvoices;
    }

    private InFileInvoice createInFileInvoice(Invoice invoice, boolean deleted) throws IOException {
        InFileInvoice inFileInvoice = new InFileInvoice(invoice, true);
        String inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(inFilenvoiceJson);
        return inFileInvoice;
    }

    private List<Invoice> getInvoices() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        List<Invoice> stringsConvertedToList = new ArrayList<>();

        for (String string : strings) {
            stringsConvertedToList.add(objectMapper.readValue(string, Invoice.class));
        }
        return stringsConvertedToList;
    }

}
