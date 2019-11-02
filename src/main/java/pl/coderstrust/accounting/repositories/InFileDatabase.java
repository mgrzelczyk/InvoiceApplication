package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    private AtomicLong counter = new AtomicLong(0);
    private final FileHelper fileHelper;
    private final ObjectMapper objectMapper;
    private Object Invoice;

    public InFileDatabase(FileHelper fileHelper, ObjectMapper objectMapper) {
        this.fileHelper = fileHelper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        if (invoice.getId() == null) {
            try {
                List<String> strings = fileHelper.readLinesFromFile();
                if (strings == null) {
                    throw new IllegalArgumentException("List is empty.");
                }
                ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
                inFileInvoices.clone();
                for (int i = 0; i < strings.size(); i++) {
                    inFileInvoices.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
                }
                Map<Long, InFileInvoice> database = new HashMap<>();
                Map<Long, InFileInvoice> databaseCopy = new HashMap<>(database);
                inFileInvoices.forEach(inFileInvoice -> databaseCopy.put(inFileInvoice.getId(), inFileInvoice));
                Long lastId = Collections.max(databaseCopy.keySet());
                InFileInvoice inFileInvoice = new InFileInvoice(invoice, false);
                inFileInvoice.setId(lastId + 1L);
                String inFilenvoiceJSON = objectMapper.writeValueAsString(inFileInvoice);
                fileHelper.writeLineToFile(inFilenvoiceJSON);
                inFileInvoice.setId(getLastId(lastId));
                String inFilenvoiceJsonLastId = objectMapper.writeValueAsString(inFileInvoice);
                fileHelper.writeLineToFile(inFilenvoiceJsonLastId);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            InFileInvoice inFileInvoice = new InFileInvoice(invoice, false);
            String inFilenvoiceJson = null;
            try {
                inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
                fileHelper.writeLineToFile(inFilenvoiceJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return invoice;
    }

    public Long getLastId(Long id) throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        Invoice invoice = new Invoice();
        InFileInvoice inFileInvoice = new InFileInvoice(invoice, false);

        Map<Long, InFileInvoice> database = new HashMap<>();
        Map<Long, InFileInvoice> databaseCopy = new HashMap<>(database);

        Long lastId = Collections.max(databaseCopy.keySet());
        inFileInvoice.setId(lastId);
        return id;
    }

    @Override
    public Invoice findInvoiceById(Long id) throws IOException {
        Map<Long, InFileInvoice> database = new HashMap<>();
        Map<Long, InFileInvoice> databaseCopy = new HashMap<>(database);
        Invoice invoice = new Invoice();
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        } else {
            fileHelper.readLinesFromFile();
            databaseCopy.get(id);
            fileHelper.writeLinesToFile((List<String>) databaseCopy);
            if (databaseCopy.containsKey(id)) {
                return invoice;
            }
            if (databaseCopy.containsKey(null)) {
                new InFileInvoice(invoice, true);
                throw new IOException("Invoice deleted.");
            }
        }
        return invoice;
    }

    @Override
    public List<Invoice> findAllnvoices() {
        List<String> invoicesList = new ArrayList<>();
        ((ArrayList<String>) invoicesList).clone();
        try {
            invoicesList = (ArrayList<String>) fileHelper.readLinesFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (List<pl.coderstrust.accounting.model.Invoice>) Invoice;
    }

    @Override
    public Invoice deleteByInvoice(Long id) throws IOException {
        Invoice invoice = new Invoice();
        Map<Long, InFileInvoice> database = new HashMap<>();
        Map<Long, InFileInvoice> databaseCopy = new HashMap<>();
        databaseCopy.putAll(database);

        fileHelper.readLinesFromFile();
        findInvoiceById(id);
        Long lastId = Collections.max(databaseCopy.keySet());
        InFileInvoice inFileInvoice = new InFileInvoice(invoice, false);
        inFileInvoice.setId(lastId);
        new InFileInvoice(invoice, true);
        saveInvoice(inFileInvoice);
        return invoice;
    }
}
