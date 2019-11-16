package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class InFileInvoiceSerialize {

    private ObjectMapper objectMapper;

    public InFileInvoiceSerialize(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String serialize(InFileInvoice inFileInvoice) throws IOException {
        String inFilenvoiceJson = objectMapper.writeValueAsString(inFileInvoice);
        return inFilenvoiceJson;
    }

    public String deserialize(String inFilenvoiceJson)
        throws IOException, JsonProcessingException {
        InFileInvoice inFileInvoice = objectMapper.readValue(inFilenvoiceJson, InFileInvoice.class);
        return inFileInvoice.toString();
    }

}
