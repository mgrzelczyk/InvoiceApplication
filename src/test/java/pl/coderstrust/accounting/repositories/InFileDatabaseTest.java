package pl.coderstrust.accounting.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.repositories.file.InFileDatabase;

import java.io.File;
import java.io.IOException;

@ActiveProfiles("file")
//@ContextConfiguration(locations = "classpath:application-in-file.properties")
//@ExtendWith(MockitoExtension.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
class InFileDatabaseTest extends DatabaseTests {

    @Autowired
    private InFileDatabase inFileDatabase;

    @Autowired
    ObjectMapper mapper;

    @Override
    InvoiceDatabase getDatabase() {
        return inFileDatabase;
    }

    @AfterEach
    public void clean() throws IOException {
        FileUtils.forceDelete(new File("database.db"));
    }

    @Test
    void test() throws IOException {
        Invoice invoice = new Invoice();
        String json = mapper.writeValueAsString(invoice);
        Invoice result = mapper.readValue(json, Invoice.class);
        assertEquals(invoice, result);
    }
}

//
//    @Mock
//    private FileHelper fileHelper;
//
//    @Mock
//    private ObjectMapper objectMapper;

//    @InjectMocks
//    private InFileDatabase inFileDatabase;
//
//    @Test
//    void shouldUpdateInvoice() throws IOException {
//        // given
//        Invoice invoiceExpected = createInvoice(1L);
//        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
//        String lineToWrite = "{\"id\":1,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}";
//
//        // when
//        when(objectMapper.writeValueAsString(any())).thenReturn(lineToWrite);
//        Invoice invoiceResult = inFileDatabase.saveInvoice(createInvoice(1L));
//
//        // then
//        verify(fileHelper, atLeast(2)).writeLineToFile(lineToWrite);
//
//        assertEquals(invoiceExpected, invoiceResult);
//    }
//
//    @Test
//    void shouldInsertInvoice() throws IOException {
//        //given
//        Invoice inputInvoice = createInvoice(1L);
//        Invoice expectedInvoice = createInvoice(1L);
//        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
//
//        //when
//        Invoice result = inFileDatabase.saveInvoice(inputInvoice);
//
//        //then
//        assertEquals(expectedInvoice, result);
//    }
//
//    @Test
//    void shouldReturnNullWhenFindInvoiceByIdThatDoesntExists() throws IOException {
//        // given
//        Invoice invoiceExpected = new Invoice(null, null, null, null, null);
//        Invoice invoice = new Invoice(null, null, null, null, null);
//        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
//        String lineToWrite = "{\"id\":null,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}";
//
//        // when
//        when(fileHelper.readLinesFromFile()).thenReturn(List.of());
//        when(objectMapper.writeValueAsString(any())).thenReturn(lineToWrite);
//
//        Invoice invoiceResult = inFileDatabase.saveInvoice(invoice);
//
//        // then
//        verify(fileHelper, atLeast(2)).writeLineToFile(lineToWrite);
//
//        assertEquals(invoiceExpected, invoiceResult);
//    }
//
//    @Test
//    void shouldReturnNullWhenFindInvoiceThatNotExists() throws IOException {
//        // given
//        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
//        Invoice invoiceFindExpected = null;
//        List<String> readedLinesFromFile = new ArrayList<>();
//        when(fileHelper.readLinesFromFile()).thenReturn(readedLinesFromFile);
//        ArrayList<InFileInvoice> inFileInvoices = new ArrayList<>();
//        for (String s : readedLinesFromFile) {
//            inFileInvoices.add(objectMapper.readValue(s, InFileInvoice.class));
//        }
//        Map<Long, InFileInvoice> database = new HashMap<>();
//        inFileInvoices.forEach(inFileInvoice -> database.put(inFileInvoice.getId(), inFileInvoice));
//
//        // when
//        Invoice invoiceFindResult = inFileDatabase.findInvoiceById(1L);
//
//        // then
//        assertEquals(invoiceFindExpected, invoiceFindResult);
//    }
//
//    @Test
//    void shouldThrownExceptionForNullWhenTryFindInvoiceWithNullID() throws IOException {
//        // given
//        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
//        // when, then
//        assertThrows(IllegalArgumentException.class,
//            () -> {
//                inFileDatabase.findInvoiceById(null);
//            });
//    }
//
//    @Test
//    void shouldFindAllnvoices() throws IOException {
//        // given
//        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        String lineToWrite = "{\"id\":10L,\"date\":2019-12-10,\"buyer\":null,\"seller\":null,\"entries\":null} " +
//            "\n{\"id\":20L,\"date\":2019-12-10,\"buyer\":null,\"seller\":null,\"entries\":null}";
//        List<String> readedLines = new ArrayList<>();
//        readedLines.add("{\"id\":10L,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}");
//        readedLines.add("{\"id\":20L,\"date\":null,\"buyer\":null,\"seller\":null,\"entries\":null}");
//
//        Invoice invoice = createInvoice(10L);
//        Invoice invoice1 = createInvoice(20L);
//
//        List<Invoice> invoicesExpect = new ArrayList<>();
//        invoicesExpect.add(invoice);
//        invoicesExpect.add(invoice1);
//
//        // when
//        String json = objectMapper.writeValueAsString(lineToWrite);
//        fileHelper.writeLineToFile(json);
//        when(fileHelper.readLinesFromFile()).thenReturn(readedLines);
//
//        List<Invoice> invoiceResult = inFileDatabase.findAllInvoices();
//
//        // then
//        assertEquals(invoicesExpect, invoiceResult);
//    }
//
//    @Test
//    void shouldThrownExceptionForNullWhenTryDeleteInvoiceWithNullID() throws IOException {
//        // given
//        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
//        // when, then
//        assertThrows(IllegalArgumentException.class,
//            () -> {
//                inFileDatabase.deleteInvoiceById(null);
//            });
//    }
//
//    @Test
//    void shouldDeleteByInvoiceId() throws IOException {
//        // given
//        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
//        Invoice invoiceDeleteExpected = createInvoice(1L);
//
//        inFileDatabase.saveInvoice(invoiceDeleteExpected);
//        invoiceDeleteExpected = inFileDatabase.findInvoiceById(1L);
//
//        //Mockito.doReturn(List.of("abc")).when(fileHelper).readLinesFromFile();
//        when(fileHelper.readLinesFromFile()).thenReturn(List.of("abc"));
//        // when
//        Invoice invoiceDeleteResult = inFileDatabase.deleteInvoiceById(0L);
//        Mockito.verify(inFileDatabase).deleteInvoiceById(1L);
//
//        // then
//        assertEquals(invoiceDeleteExpected, invoiceDeleteResult);
//    }
//
//    @Test
//    void fileDatabaseShouldBeEmptyAfterIntitalize() throws IOException {
//        // given
//        inFileDatabase = new InFileDatabase(fileHelper, objectMapper);
//        String lineToWrite = "";
//        String json = objectMapper.writeValueAsString(lineToWrite);
//        fileHelper.writeLineToFile(json);
//        List<String> readedLines = new ArrayList<>();
//
//        // when
//        when(fileHelper.readLinesFromFile()).thenReturn(readedLines);
//        List<Invoice> invoices = inFileDatabase.findAllInvoices();
//
//        // then
//        assertEquals(0, invoices.size());
//    }
//
//    private Invoice createInvoice(Long id) {
//        Invoice invoice;
//        LocalDate date = LocalDate.of(2019, 12, 10);
//        Company buyer = new Company(1L, "tin#1", "buyer address", "buyer name");
//        Company seller = new Company(2L, "tin#2", "seller address", "seller name");
//        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
//        invoice = new Invoice(id, date, buyer, seller, invoiceEntries);
//        return invoice;
//    }
//
//}
