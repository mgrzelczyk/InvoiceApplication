package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

class InFileInvoiceSerialize {

    private final ObjectMapper objectMapper;

    public InFileInvoiceSerialize(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String serialize(InFileInvoice inFileInvoice) throws IOException {
        return objectMapper.writeValueAsString(inFileInvoice);
    }

    public InFileInvoice deserialize(String inFilenvoiceJson) throws IOException {
        InFileInvoice inFileInvoice = objectMapper.readValue(inFilenvoiceJson, InFileInvoice.class);
        return objectMapper.readValue(inFilenvoiceJson, InFileInvoice.class);
    }

}
