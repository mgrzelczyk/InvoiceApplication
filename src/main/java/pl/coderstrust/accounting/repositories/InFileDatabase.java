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
                insertInvoice(invoice);
        } else {
            InFileInvoice inFileInvoice = updateInvoice(invoice, false);
            String inFilenvoiceJson = null;
            inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
            fileHelper.writeLineToFile(inFilenvoiceJson);
            counter.incrementAndGet();
        }
        return invoice;
    }

    @Override
    public Invoice findInvoiceById(Long id) throws IOException {
        Invoice invoice;
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        } else {
            invoice = loadInvoices().get(id);
        }
        return invoice;
    }

    @Override
    public List<Invoice> findAllInvoices() throws IOException {
        return getInvoices();
    }

    @Override
    public Invoice deleteInvoiceById(Long id) throws IOException {
        Invoice invoice = new Invoice();
        InFileInvoice inFileInvoice;
        Map<Long, InFileInvoice> database = loadInvoices();

        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        } else {
            getInvoices();
            inFileInvoice = database.get(id);
            inFileInvoice = updateInvoice(invoice, true);
            database.remove(id);
            if (inFileInvoice.getDeleted(true)) {
                invoice = inFileInvoice;
            }
        }
        return invoice;
    }

    private Long getLastId() throws IOException {
        return Collections.max(loadInvoices().keySet());
    }

    private Map<Long, InFileInvoice> loadInvoices() throws IOException {
        List<InFileInvoice> inFileInvoices = insertInvoice();
        Map<Long, InFileInvoice> database = new HashMap<>();
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        return database;
    }

    private void insertInvoice(Invoice invoice) throws IOException {
        Long lastId = getLastId();
        InFileInvoice inFileInvoice = updateInvoice(invoice, false);
        inFileInvoice.setId(lastId + 1L);
        String inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(inFilenvoiceJson);
        counter.incrementAndGet();
        inFileInvoice.setId(getLastId());
        String inFilenvoiceJsonLastId = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(inFilenvoiceJsonLastId);
    }

    private List<InFileInvoice> insertInvoice() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            inFileInvoices.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
        }
        return inFileInvoices;
    }

    private InFileInvoice updateInvoice(Invoice invoice, boolean deleted) throws IOException {
        InFileInvoice inFileInvoice = new InFileInvoice(invoice, true);
        String inFilenvoiceJson = null;
        inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(inFilenvoiceJson);
        counter.incrementAndGet();
        return inFileInvoice;
    }

    private List<Invoice> getInvoices() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        List<Invoice> stringsConvertedToList = new ArrayList<>();

        for (String string : strings) {
            stringsConvertedToList.add(objectMapper.readValue(string, InFileInvoice.class));
        }
        return stringsConvertedToList;
    }

}
