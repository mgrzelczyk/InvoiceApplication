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

    private AtomicLong counter = new AtomicLong(0);
    private FileHelper fileHelper;
    private ObjectMapper objectMapper;
    private InFileInvoiceSerialize inFileInvoiceSerialize;

    public InFileDatabase(FileHelper fileHelper, ObjectMapper objectMapper) throws IOException {
        this.fileHelper = fileHelper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) throws 
        JsonProcessingException, IOException {
        if (invoice.getId() == null) {
            try {
                Map<Long, InFileInvoice> database = loadInvoices();
                counter.incrementAndGet();
                Long lastId = getLastId();
                InFileInvoice inFileInvoice = updateDeleteInvoice(invoice, false);
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
            InFileInvoice inFileInvoice = updateDeleteInvoice(invoice, false);
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
        return Collections.max(loadInvoices().keySet());
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
        return getInvoices();
    }

    @Override
    public Invoice deleteByInvoice(Long id) throws IOException {
        Invoice invoice;
        Map<Long, InFileInvoice> database = loadInvoices();

        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        } else {
            getInvoices();
            invoice = database.get(id);
            InFileInvoice inFileInvoice = updateDeleteInvoice(invoice, true);
            database.remove(id);
            if (inFileInvoice.getDeleted(true)) {
                invoice = inFileInvoice;
            }
        }
        return invoice;
    }

    private Map<Long, InFileInvoice> loadInvoices() throws IOException {
        List<InFileInvoice> inFileInvoices = insertInvoice();
        Map<Long, InFileInvoice> database = new HashMap<>();
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        return database;
    }

    private List<InFileInvoice> insertInvoice() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile(DATABASE_FILE_NAME);
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            inFileInvoices.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
        }
        return inFileInvoices;
    }

    private InFileInvoice updateDeleteInvoice(Invoice invoice, boolean deleted) {
        return new InFileInvoice(invoice, true);
    }

    private List<Invoice> getInvoices() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile(DATABASE_FILE_NAME);
        List<Invoice> stringsConvertedToList = new ArrayList<>();

        for (int i = 0; i < strings.size(); i++) {
            stringsConvertedToList.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
        }
        return stringsConvertedToList;
    }
}
