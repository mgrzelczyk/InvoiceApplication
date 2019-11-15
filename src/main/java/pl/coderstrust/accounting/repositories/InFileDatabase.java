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

    private Long lastId;
    private final AtomicLong counter = new AtomicLong(getLastId(lastId));
    private FileHelper fileHelper;
    private ObjectMapper objectMapper;
    private InFileInvoiceSerializer inFileInvoiceSerializer = new InFileInvoiceSerializer(fileHelper);

    public InFileDatabase(FileHelper fileHelper, ObjectMapper objectMapper) throws IOException {
        this.fileHelper = fileHelper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) throws IOException {
        InFileInvoice inFileInvoice = new InFileInvoice();
        if (invoice.getId() == null) {
            try {
                loadInvoices();
                counter.incrementAndGet();
                getLastId(lastId);
                inFileInvoice.setId(lastId + 1L);
                inFileInvoiceSerializer.toStrings();
                inFileInvoice.setId(getLastId(lastId));
                String inFilenvoiceJsonLastId = objectMapper.writeValueAsString(inFileInvoice);
                fileHelper.writeLineToFile(inFilenvoiceJsonLastId);
            } catch (JsonParseException e) {
                throw new IOException(e);
            }
        } else {
            try {
                inFileInvoiceSerializer.toStrings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return invoice;
    }

    public Long getLastId(Long lastId) throws IOException {
        Map<Long, InFileInvoice> database = new HashMap<>();
        loadInvoices();
        lastId = Collections.max(database.keySet());

        return lastId;
    }

    @Override
    public Invoice findInvoiceById(Long id) throws IOException {
        Invoice invoice;
        Map<Long, InFileInvoice> database = new HashMap<>();
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        }
        loadInvoices();
        invoice = database.get(id);
        return invoice;
    }

    @Override
    public List<Invoice> findAllnvoices() throws IOException {
        List<Invoice> convertLoadedInvoice = new ArrayList<>(loadInvoices());
        return convertLoadedInvoice;
    }

    @Override
    public Invoice deleteByInvoice(Long id) throws IOException {
        Invoice invoice;
        InFileInvoice inFileInvoice = new InFileInvoice();
        Map<Long, InFileInvoice> database = new HashMap<>();
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        }
        loadInvoices();
        invoice = database.get(id);
        updateDelete(invoice, false);
        database.remove(id);
        inFileInvoice.setDeleted(true);
        if (inFileInvoice.getDeleted(true)) {
            invoice = inFileInvoice;
        }
        return invoice;
    }

    private List<String> insertInvoice() throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        return strings;
    }

    private ArrayList<InFileInvoice> loadInvoices() throws IOException {
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        Map<Long, InFileInvoice> database = new HashMap<>();
        insertInvoice();
        for (int i = 0; i < insertInvoice().size(); i++) {
            inFileInvoices.add(objectMapper.readValue(insertInvoice().get(i), InFileInvoice.class));
        }
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        return inFileInvoices;
    }

    private InFileInvoice updateDelete(Invoice invoice, boolean deleted) {
        return new InFileInvoice();
    }
}
