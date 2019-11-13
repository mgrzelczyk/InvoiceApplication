package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class InFileInvoiceSerializer {

    ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
    private final FileHelper fileHelper;
    private ObjectMapper objectMapper = new ObjectMapper();

    public InFileInvoiceSerializer(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    List<InFileInvoice> fromStrings(List<String> strings) throws IOException {

        List<String> insertInvoice = fileHelper.readLinesFromFile();
        for (int i = 0; i < insertInvoice.size(); i++) {
            inFileInvoices.add(objectMapper.readValue(insertInvoice.get(i), InFileInvoice.class));
        }
        Map<Long, InFileInvoice> database = new HashMap<>();
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        return inFileInvoices;
    }
}
