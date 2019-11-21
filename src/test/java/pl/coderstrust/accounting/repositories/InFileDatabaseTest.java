package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class InFileDatabaseTest {

    @Mock
    private FileHelper fileHelper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private InFileDatabase inFileDatabase;


    @Test
    void shouldSaveInvoice() throws IOException {
        LocalDateTime date = LocalDateTime.of(2019,11,20,20,20,19);
        Company buyer = new Company(1L, "tin#1", "buyer address", "buyer name");
        Company seller = new Company(2L, "tin#2", "seller address", "seller name");
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Invoice invoiceExpected = new Invoice(1L, date, buyer, seller, invoiceEntries);
        Invoice invoice = new Invoice(1L, date, buyer, seller, invoiceEntries);
        InFileInvoice inFileInvoice = new InFileInvoice();
        inFileInvoice.setId(2L);
        List<String> invoiceListInput = new ArrayList<>();
        String filePath = "database.db";
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        String lineToWrite = "{\"id\":9,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}";

        when(objectMapper.writeValueAsString(any())).thenReturn("lineToWrite");

        Invoice invoiceResult = inFileDatabase.saveInvoice(invoice);

        verify(fileHelper).writeLineToFile("lineToWrite");

        assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    void shouldReturnNullForNullSave() {
    }

    @Test
    void shouldGetLastId() throws IOException {
        Invoice invoice = new Invoice();
        InFileInvoice inFileInvoiceExpected = new InFileInvoice();
        List<String> invoiceListInput = new ArrayList<>();
        inFileInvoiceExpected.setId(1L);
        String filePath = "database.db";

        when(inFileDatabase.getLastId()).thenReturn(1L);
    }

    @Test
    void shouldFindInvoiceById() throws IOException {
        Invoice invoice = new Invoice();
        InFileInvoice inFileInvoiceExpected = new InFileInvoice();
        List<String> invoiceListInput = new ArrayList<>();
        inFileInvoiceExpected.setId(1L);
        String filePath = "database.db";

        when(inFileDatabase.findInvoiceById(1L)).thenReturn(invoice);
    }

    @Test
    void shouldFindAllnvoices() throws IOException {
        List<Invoice> invoiceList = new ArrayList<>();

        when(inFileDatabase.findAllnvoices()).thenReturn(invoiceList);

        verify(inFileDatabase.findAllnvoices());
    }

    @Test
    void shouldDeleteByInvoice()  {
    }
}
