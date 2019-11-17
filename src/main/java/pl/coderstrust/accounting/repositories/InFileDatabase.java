package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static pl.coderstrust.accounting.application.Properties.DATABASE_FILE_NAME;
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

    private AtomicLong counter;
    private final FileHelper fileHelper;
    private ObjectMapper objectMapper;
    InFileInvoiceSerialize inFileInvoiceSerialize;

    public InFileDatabase(FileHelper fileHelper, ObjectMapper objectMapper) throws IOException {
        this.fileHelper = fileHelper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) throws JsonProcessingException {
        if (invoice.getId() == null) {
            try {
                Map<Long, InFileInvoice> database = loadInvoices();
                counter.incrementAndGet();
                Long lastId = Collections.max(database.keySet());

                InFileInvoice inFileInvoice = updateDeleteInvoice(invoice);
                inFileInvoice.setId(lastId + 1L);
                String inFilenvoiceJson = inFileInvoiceSerialize.serialize(inFileInvoice);
                fileHelper.writeLineToFile(inFilenvoiceJson);
                inFileInvoice.setId(getLastId());
                String inFilenvoiceJsonLastId = inFileInvoiceSerialize.serialize(inFileInvoice);
                fileHelper.writeLineToFile(inFilenvoiceJsonLastId);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            InFileInvoice inFileInvoice = updateDeleteInvoice(invoice);
            String inFilenvoiceJson = null;
            try {
                inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
                fileHelper.writeLineToFile(inFilenvoiceJson);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return invoice;
    }

    public Long getLastId() throws IOException {
        Long lastId = Collections.max(loadInvoices().keySet());
        return lastId;
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
    public List<Invoice> findAllnvoices() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile(DATABASE_FILE_NAME);
        List<Invoice> stringsConvertedToList = new ArrayList<>();
        Map<Long, Class<InFileInvoice>> database = new HashMap<>();

        for (int i = 0; i < strings.size(); i++) {
            stringsConvertedToList.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
        }
        stringsConvertedToList.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), InFileInvoice.class));

        return stringsConvertedToList;
    }

    @Override
    public Invoice deleteByInvoice(Long id) throws IOException {
        Invoice invoice;
        List<String> strings = fileHelper.readLinesFromFile(DATABASE_FILE_NAME);
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
            InFileInvoice inFileInvoice = updateDeleteInvoice(invoice);
            database.remove(id);
            inFileInvoice.setDeleted(true);
            invoice = inFileInvoice;
        }
        return invoice;
    }

    private Map<Long, InFileInvoice> loadInvoices() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile(DATABASE_FILE_NAME);
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            inFileInvoices.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
        }
        Map<Long, InFileInvoice> database = new HashMap<>();
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        return database;
    }

    private InFileInvoice updateDeleteInvoice(Invoice invoice) {
        return new InFileInvoice(invoice, false);
    }
}
