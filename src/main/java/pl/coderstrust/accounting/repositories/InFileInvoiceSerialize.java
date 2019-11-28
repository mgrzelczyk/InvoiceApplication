package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public String deserialize(String inFilenvoiceJson)
        throws IOException, JsonProcessingException {
        InFileInvoice inFileInvoice = objectMapper.readValue(inFilenvoiceJson, InFileInvoice.class);
        return inFileInvoice.toString();
    }

}
