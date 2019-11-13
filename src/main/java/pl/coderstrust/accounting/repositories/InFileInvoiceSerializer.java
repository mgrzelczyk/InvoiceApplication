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
        return InFileInvoice;
    }
}
