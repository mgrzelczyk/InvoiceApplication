package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;

import java.io.DataInput;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
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
                inFileInvoices.clone();
                for (int i = 0; i < strings.size(); i++) {
                    inFileInvoices.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
                }
                Map<Long, InFileInvoice> database = new HashMap<>();
                Map<Long, InFileInvoice> databaseCopy = new HashMap<>(database);
                inFileInvoices.forEach(inFileInvoice -> databaseCopy.put(inFileInvoice.getId(), inFileInvoice));
                counter.incrementAndGet();
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
        if (strings == null) {
            throw new IllegalArgumentException("List is empty.");
        }
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        inFileInvoices.clone();
//        for (int i = 0; i < strings.size(); i++) {
//            inFileInvoices.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
//        }
        objectMapper.readTree(strings.toString());
        ObjectReader objectReader = objectMapper.reader().forType(new TypeReference<List<String>>(){});

        Map<Long, InFileInvoice> database = new HashMap<>();
        Map<Long, InFileInvoice> databaseCopy = new HashMap<>(database);
        inFileInvoices.forEach(inFileInvoice -> databaseCopy.put(inFileInvoice.getId(), inFileInvoice));

        return Collections.max(databaseCopy.keySet());
    }

    @Override
    public Invoice findInvoiceById(Long id) throws IOException {
        Invoice invoice = new Invoice();
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        } else {
            List<String> strings = fileHelper.readLinesFromFile();
            ObjectReader objectReader = objectMapper.reader().forType(new TypeReference<List<String>>(){});
            // ArrayList<String> result = objectReader.readValue((JsonParser) strings);
            InFileInvoice readFromFileInvoices = null;
//            for (int i = 0; i < strings.size(); i++) {
//                readFromFileInvoices = objectMapper.readValue(strings, InFileInvoice.class);
//            }

            JsonNode node = objectMapper.createObjectNode();
            node = objectMapper.valueToTree(strings);

            JsonNode root = objectReader.readTree(strings.toString());

            id = root.path("id").asLong();
            JsonNode dateNode = root.path("date");
            JsonNode buyerNode = root.path("buyer");
            JsonNode sellerNode = root.path("seller");
            JsonNode entriesNode = root.path("entries");
            System.out.println("Node tree: " + node);
            System.out.println("Read tree: " + root);
            System.out.println("id: " + id);
            System.out.println("date Node: " + dateNode);
            System.out.println("buyer Node: " + buyerNode);
            System.out.println("seller Node: " + sellerNode);
            System.out.println("entries Node: " + entriesNode);

        }


            // JSONPObject jsonpObject = (JSONPObject) strings;
            //Invoice invoiceResult = new Invoice(invoice.getId(), invoice.getDate());
            // JsonNode array = objectMapper.readValue((JsonParser) strings, JsonNode.class);
            // for (int i = 0; i < array.size(); i++) {
            // JsonNode jsonNode = array.get(i);
            // JsonNode idNode = jsonNode.get("ID");
            // String ID = idNode.asText();


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
        Long lastID = Collections.max(databaseCopy.keySet());
        InFileInvoice inFileInvoice = new InFileInvoice(invoice, false);
        inFileInvoice.setId(lastID);
        inFileInvoice.setDeleted(true);
        saveInvoice(inFileInvoice);
        return invoice;
    }
}
