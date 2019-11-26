package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        String lineToWrite = "{\"id\":1,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}";

        when(objectMapper.writeValueAsString(any())).thenReturn(lineToWrite);

        Invoice invoiceResult = inFileDatabase.saveInvoice(invoice);

        verify(fileHelper).writeLineToFile(lineToWrite);

        assertEquals(invoiceExpected, invoiceResult);
        System.out.println((invoiceResult));
    }

    @Test
    void shouldFindInvoiceByIdForNullInvoice() throws IOException {
        Invoice invoiceFindExpected = null;
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        String filePath = "database.db";
        List<String> readedLinesFromFile = new ArrayList<>();
        given(fileHelper.readLinesFromFile()).willReturn(readedLinesFromFile);
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        for (String s : readedLinesFromFile) {
            inFileInvoices.add(objectMapper.readValue(s, InFileInvoice.class));
        }
        Map<Long, InFileInvoice> database = new HashMap<>();
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        System.out.println("Database:" + database);
        System.out.println("Read Lines: " + fileHelper.readLinesFromFile());

        Invoice invoiceFindResult = inFileDatabase.findInvoiceById(1L);

        assertEquals(invoiceFindExpected, invoiceFindResult);
    }

    @Test
    void shouldThrownExceptionForNullWhenTryFindInvoiceWithNullID() throws IOException {
        Invoice invoiceFindExpected = new Invoice();
        invoiceFindExpected.setId(null);
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        String filePath = "database.db";
        List<String> readedLinesFromFile = new ArrayList<>();
        given(fileHelper.readLinesFromFile()).willReturn(readedLinesFromFile);
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        for (String s : readedLinesFromFile) {
            inFileInvoices.add(objectMapper.readValue(s, InFileInvoice.class));
        }
        Map<Long, InFileInvoice> database = new HashMap<>();
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
        when(inFileDatabase.findInvoiceById(null)).thenThrow(IllegalArgumentException.class);

        Invoice invoiceFindResult = inFileDatabase.findInvoiceById(null);

        assertEquals(invoiceFindExpected, invoiceFindResult);
    }

    @Test
    void shouldFindAllnvoices() throws IOException {
        ArrayList<String> readedLinesFromFile = new ArrayList<>();
        List<Invoice> invoicesExpected = new ArrayList<>();
        for (String s : readedLinesFromFile) {
            invoicesExpected.add(objectMapper.readValue(s, InFileInvoice.class));
        }

        ArrayList<Invoice> invoicesResult = new ArrayList<>();

        assertEquals(invoicesExpected, invoicesResult);
    }

    @Test
    void shouldDeleteByInvoice() throws IOException {
        String filePath = "database.db";
        List<String> readedLinesFromFile = new ArrayList<>();
        String lineToWrite2 = "{\"id\":1,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}";
        given(fileHelper.readLinesFromFile()).willReturn(readedLinesFromFile);
        Invoice invoiceDeleteExpected = inFileDatabase.deleteInvoiceById(1L);
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        for (String s : readedLinesFromFile) {
            inFileInvoices.add(objectMapper.readValue(s, InFileInvoice.class));
        }
        Map<Long, InFileInvoice> database = new HashMap<>();
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));

        Invoice invoiceDeleteResult = inFileDatabase.deleteInvoiceById(1L);

        assertEquals(invoiceDeleteExpected, invoiceDeleteResult);
    }

}
