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


    private Invoice createInvoice() {
        LocalDateTime date = LocalDateTime.of(2019,11,20,20,20,19);
        Company buyer = new Company(1L, "tin#1", "buyer address", "buyer name");
        Company seller = new Company(2L, "tin#2", "seller address", "seller name");
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Invoice invoice = new Invoice(1L, date, buyer, seller, invoiceEntries);
        return invoice;
    }

    @Test
    void shouldUpdateInvoice() throws IOException {
        // given
        Invoice invoiceExpected = createInvoice();
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        String lineToWrite = "{\"id\":1,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}";

        // when
        when(objectMapper.writeValueAsString(any())).thenReturn(lineToWrite);
        Invoice invoiceResult = inFileDatabase.saveInvoice(createInvoice());

        // then
        verify(fileHelper, atLeast(2)).writeLineToFile(lineToWrite);

        assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    void shouldInsertInvoice() throws IOException {
        //given
        Invoice invoiceExpected = createInvoice();
        invoiceExpected.setId(null);
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        inFileDatabase.saveInvoice(invoiceExpected);
        invoiceExpected.setId(1L);
        invoiceExpected = inFileDatabase.saveInvoice(invoiceExpected);
        Invoice invoiceResult = createInvoice();
        invoiceResult.setId(1L);

        //when
        invoiceResult = inFileDatabase.saveInvoice(invoiceResult);

        //then
        assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    void shouldReturnNullWhenFindInvoiceByIdThatDoesntExists() throws IOException {
        // given
        Invoice invoiceExpected = new Invoice(null, null, null, null, null);
        Invoice invoice = new Invoice(null, null, null, null, null);
        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
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
    void shouldFindInvoiceByIdForNullInvoice() throws IOException {
        // given
        Invoice invoiceFindExpected = null;
        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
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
        // given, when
        ArrayList<String> readedLinesFromFile = new ArrayList<>();
        List<Invoice> invoicesExpected = new ArrayList<>();
        for (String s : readedLinesFromFile) {
            invoicesExpected.add(objectMapper.readValue(s, InFileInvoice.class));
        }
        ArrayList<Invoice> invoicesResult = new ArrayList<>();

        // then
        assertEquals(invoicesExpected, invoicesResult);
    }

    @Test
    void shouldThrownExceptionForNullWhenTryDeleteInvoiceWithNullID() throws IOException {
        // given, when, then
        assertThrows(IllegalArgumentException.class,
            () -> {
                inFileDatabase.deleteInvoiceById(null);
            });
    }

    @Test
    void shouldDeleteByInvoiceId() throws IOException {
        // given
        Invoice invoiceDeleteExpected = createInvoice();
        InFileDatabase inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
        invoiceDeleteExpected = inFileDatabase.findInvoiceById(1L);

        // when
        Invoice invoiceDeleteResult = inFileDatabase.deleteInvoiceById(1L);

        // then
        assertEquals(invoiceDeleteExpected, invoiceDeleteResult);
    }

}
