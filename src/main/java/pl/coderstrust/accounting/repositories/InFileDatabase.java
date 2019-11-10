package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
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
    private ObjectMapper objectMapper = new ObjectMapper();
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
                for (int i = 0; i < strings.size(); i++) {
                    inFileInvoices.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
                }
                Map<Long, InFileInvoice> database = new HashMap<>();
                inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
                counter.incrementAndGet();
                Long lastId = Collections.max(database.keySet());

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return invoice;
    }

    public Long getLastId(Long id) throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        List<InFileInvoice> stringsConvertedToList = new ArrayList<>();
        Map<Long, InFileInvoice> database = new HashMap<>();
        Long lastId;

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
}
