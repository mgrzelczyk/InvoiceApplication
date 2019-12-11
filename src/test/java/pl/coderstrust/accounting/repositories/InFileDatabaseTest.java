package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class InFileDatabaseTest {

    @Mock
    private FileHelper fileHelper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private InFileDatabase inFileDatabase;

    private Invoice createInvoice(Long id) {
        LocalDateTime date = LocalDateTime.of(2019,11,20,20,20,19);
        Company buyer = new Company(1L, "tin#1", "buyer address", "buyer name");
        Company seller = new Company(2L, "tin#2", "seller address", "seller name");
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Invoice invoice = new Invoice(id, date, buyer, seller, invoiceEntries);
        return invoice;
    }

    @Test
    void shouldUpdateInvoice() throws IOException {
        // given
        Invoice invoiceExpected = createInvoice(1L);
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        String lineToWrite = "{\"id\":1,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}";

        // when
        when(objectMapper.writeValueAsString(any())).thenReturn(lineToWrite);
        Invoice invoiceResult = inFileDatabase.saveInvoice(createInvoice(1L));

        // then
        verify(fileHelper, atLeast(2)).writeLineToFile(lineToWrite);

        assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    void shouldInsertInvoice() throws IOException {
        //given
        Invoice inputInvoice = createInvoice(1L);
        Invoice expectedInvoice = createInvoice(1L);
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);

        //when
        Invoice result = inFileDatabase.saveInvoice(inputInvoice);

        //then
        assertEquals(expectedInvoice, result);
    }

    @Test
    void shouldReturnNullWhenFindInvoiceByIdThatDoesntExists() throws IOException {
        // given
        Invoice invoiceExpected = new Invoice(null, null, null, null, null);
        Invoice invoice = new Invoice(null, null, null, null, null);
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        String lineToWrite = "{\"id\":null,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}";

        // when
        when(fileHelper.readLinesFromFile()).thenReturn(List.of());
        when(objectMapper.writeValueAsString(any())).thenReturn(lineToWrite);

        Invoice invoiceResult = inFileDatabase.saveInvoice(invoice);

        // then
        verify(fileHelper, atLeast(2)).writeLineToFile(lineToWrite);

        assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    void shouldReturnNullWhenFindInvoiceThatNotExists() throws IOException {
        // given
        Invoice invoiceFindExpected = null;
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        List<String> readedLinesFromFile = new ArrayList<>();
        when(fileHelper.readLinesFromFile()).thenReturn(readedLinesFromFile);
        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
        for (String s : readedLinesFromFile) {
            inFileInvoices.add(objectMapper.readValue(s, InFileInvoice.class));
        }
        Map<Long, InFileInvoice> database = new HashMap<>();
        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));

        // when
        Invoice invoiceFindResult = inFileDatabase.findInvoiceById(1L);

        // then
        assertEquals(invoiceFindExpected, invoiceFindResult);
    }

    @Test
    void shouldThrownExceptionForNullWhenTryFindInvoiceWithNullID() throws IOException {
        // given, when, then
        assertThrows(IllegalArgumentException.class,
            () -> {
                inFileDatabase.findInvoiceById(null);
            });
    }

    @Test
    void shouldFindAllnvoices() throws IOException {
        // given
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        ArrayList<String> invoicesExpected = new ArrayList<>();
        invoicesExpected.add("{\"id\":10L,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}");
        invoicesExpected.add("{\"id\":20L,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}");
        String lineToWrite = "{\"id\":10L,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null} " +
            "\n{\"id\":20L,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}";
        List<String> readedLines = new ArrayList<>();
        readedLines.add("{\"id\":10L,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}");
        readedLines.add("{\"id\":20L,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}");

        Invoice invoice = createInvoice(5L);
        Invoice invoice1 = createInvoice(6L);

        List<Invoice> invoicesExpect = new ArrayList<>();
        invoicesExpect.add(invoice);
        invoicesExpect.add(invoice1);

        // when
        String json = objectMapper.writeValueAsString(lineToWrite);
        fileHelper.writeLineToFile(json);
        when(fileHelper.readLinesFromFile()).thenReturn(readedLines);

        List<Invoice> invoiceResult = new ArrayList<>();
        for (String string : readedLines) {
            invoiceResult.add(objectMapper.readValue(string, Invoice.class));
        }

        invoiceResult = inFileDatabase.findAllInvoices();

        // then
        assertEquals(invoicesExpect, invoiceResult);
    }

    @Test
    void shouldThrownExceptionForNullWhenTryDeleteInvoiceWithNullID() throws IOException {
        // given
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        // when, then
        assertThrows(IllegalArgumentException.class,
            () -> {
                inFileDatabase.deleteInvoiceById(null);
            });
    }

    @Test
    void shouldDeleteByInvoiceId() throws IOException {
        // given
        Invoice invoiceDeleteExpected = createInvoice(0L);
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        //inFileDatabase.saveInvoice(invoiceDeleteExpected);
        invoiceDeleteExpected = inFileDatabase.findInvoiceById(0L);

        // when
        Invoice invoiceDeleteResult = inFileDatabase.deleteInvoiceById(0L);

        // then
        assertEquals(null, invoiceDeleteResult);
    }

    @Test
    void fileDatabaseShouldBeEmptyAfterIntitalize() throws IOException {
        // given
        InFileDatabase database = new InFileDatabase(fileHelper, objectMapper);
        String lineToWrite = "";
        String json = objectMapper.writeValueAsString(lineToWrite);
        fileHelper.writeLineToFile(json);
        List<String> readedLines = new ArrayList<>();

        // when
        when(fileHelper.readLinesFromFile()).thenReturn(readedLines);
        List<Invoice> invoices = database.findAllInvoices();

        // then
        assertEquals(0, invoices.size());
    }

}
