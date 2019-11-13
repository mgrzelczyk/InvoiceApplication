package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.core.JsonParseException;
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

    private final AtomicLong counter = new AtomicLong(getLastId(1L));
    private final FileHelper fileHelper;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
    Map<Long, InFileInvoice> database = new HashMap<>();

    public InFileDatabase(FileHelper fileHelper, ObjectMapper objectMapper, Object invoice) throws IOException {
        this.fileHelper = fileHelper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) throws IOException {
        if (invoice.getId() == null) {
            try {
                loadInvoices();
                counter.incrementAndGet();
                Long lastId = Collections.max(database.keySet());
                InFileInvoice inFileInvoice = new InFileInvoice(invoice, false);
                inFileInvoice.setId(lastId + 1L);
                String inFilenvoiceJSON = objectMapper.writeValueAsString(inFileInvoice);
                fileHelper.writeLineToFile(inFilenvoiceJSON);
                inFileInvoice.setId(getLastId(lastId));
                String inFilenvoiceJsonLastId = objectMapper.writeValueAsString(inFileInvoice);
                fileHelper.writeLineToFile(inFilenvoiceJsonLastId);
            } catch (JsonParseException e) {
                throw new IOException(e);
            }
        } else {
            InFileInvoice inFileInvoice = new InFileInvoice(invoice, false);
            String inFilenvoiceJson = null;
            try {
                inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
                fileHelper.writeLineToFile(inFilenvoiceJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return invoice;
    }

    public Long getLastId(Long id) throws IOException {
        Long lastId;
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        } else {
            loadInvoices();
            lastId = Collections.max(database.keySet());
        }
        return lastId;
    }

    @Override
    public Invoice findInvoiceById(Long id) throws IOException {
        Invoice invoice;
        List<String> strings = fileHelper.readLinesFromFile();
        List<InFileInvoice> stringsConvertedToList = new ArrayList<>();
        Map<Long, InFileInvoice> database = new HashMap<>();

        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        } else {
            if (strings == null) {
                throw new IllegalArgumentException("List is empty.");
            }

            for (int i = 0; i < strings.size(); i++) {
                stringsConvertedToList.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
            }
            stringsConvertedToList.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
            invoice = database.get(id);

        }
        return invoice;
    }

    @Override
    public List<Invoice> findAllnvoices() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        List<Invoice> stringsConvertedToList = new ArrayList<>();
        Map<Long, Class<InFileInvoice>> database = new HashMap<>();

        if (strings == null) {
            throw new IllegalArgumentException("List is empty.");
        }

        for (int i = 0; i < strings.size(); i++) {
            stringsConvertedToList.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
        }
        stringsConvertedToList.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), InFileInvoice.class));

        return stringsConvertedToList;
    }

    @Override
    public Invoice deleteByInvoice(Long id) throws IOException {
        Invoice invoice;
        List<String> strings = fileHelper.readLinesFromFile();
        List<InFileInvoice> stringsConvertedToList = new ArrayList<>();
        Map<Long, InFileInvoice> database = new HashMap<>();

        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        } else {
            if (strings == null) {
                throw new IllegalArgumentException("List is empty.");
            }
            for (int i = 0; i < strings.size(); i++) {
                stringsConvertedToList.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
            }

            stringsConvertedToList.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));

            invoice = database.get(id);
            InFileInvoice inFileInvoice = new InFileInvoice(invoice, false);
            database.remove(id);
            inFileInvoice.setDeleted(true);
            invoice = inFileInvoice;
        }
        return invoice;
    }

    private List insertInvoice (String invoice) throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        return strings;
    }

    private ArrayList<InFileInvoice> loadInvoices() throws IOException {
        List<String> insertInvoice = fileHelper.readLinesFromFile();
        for (int i = 0; i < insertInvoice.size(); i++) {
            inFileInvoices.add(objectMapper.readValue(insertInvoice.get(i), InFileInvoice.class));
        }
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        return inFileInvoices;
    }

}
