package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InFileInvoiceSerializer {

    private final FileHelper fileHelper;
    private ObjectMapper objectMapper;
    private Invoice invoice;
    private InFileInvoice inFileInvoice = new InFileInvoice(invoice, false);

    public InFileInvoiceSerializer(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    public String toStrings () throws IOException {
        String inFilenvoiceJSON = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(inFilenvoiceJSON);
        return inFilenvoiceJSON;
    }

    public Map<Long, InFileInvoice> fromStrings () throws IOException {
        List<String> strings = fileHelper.readLinesFromFile();
        Map<Long, InFileInvoice> database = new HashMap<>();
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            inFileInvoices.add(objectMapper.readValue(strings.get(i), InFileInvoice.class));
        }
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        return database;
    }

}
