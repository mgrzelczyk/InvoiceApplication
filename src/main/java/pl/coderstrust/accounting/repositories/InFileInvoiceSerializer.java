package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InFileInvoiceSerializer {

    private ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
    private final FileHelper fileHelper;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<Long, InFileInvoice> database = new HashMap<>();
    private Invoice invoice;
    private InFileInvoice inFileInvoice = new InFileInvoice(invoice, false);

    public InFileInvoiceSerializer(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    public String fromStrings () throws IOException {
        String inFilenvoiceJSON = objectMapper.writeValueAsString(inFileInvoice);
        fileHelper.writeLineToFile(inFilenvoiceJSON);
        return inFilenvoiceJSON;
    }
}
