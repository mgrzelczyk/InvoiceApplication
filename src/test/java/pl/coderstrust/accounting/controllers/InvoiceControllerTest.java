package pl.coderstrust.accounting.controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;
import pl.coderstrust.accounting.services.InvoiceBook;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = InvoiceController.class)
class InvoiceControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceBook service;

    @Test
    @DisplayName("Should return 3 object")
    void shouldReturn3Invoice() throws Exception {
        List<Invoice> invoices = prepareInvoices();

        when(service.findAllInvoices()).thenReturn(invoices);

        MvcResult mvcResult = mockMvc.perform(get("/api/invoices/")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isOk())
            .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();
        Invoice[] mappedInvoices = objectMapper.readValue(jsonString, Invoice[].class);

        assertEquals(mappedInvoices.length, 3);
    }

    @Test
    @DisplayName("Should check first object in table")
    void shouldCheckFirstInvoiceInTable() throws Exception {
        List<Invoice> invoices = prepareInvoices();
        Invoice invoice = invoices.get(0);

        when(service.findAllInvoices()).thenReturn(invoices);

        MvcResult mvcResult = mockMvc.perform(get("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isOk())
            .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();
        Invoice[] mappedInvoices = objectMapper.readValue(jsonString, Invoice[].class);
        Invoice mappedInvoice = mappedInvoices[0];

        assertEquals(invoice, mappedInvoice);
    }

    @Test
    @DisplayName("Should find one object by date")
    void shouldFindInvoiceByDate() throws Exception {
        Invoice invoice = prepareInvoice();
        invoice.setId(1L);
        invoice.setDate(LocalDate.of(2018, 12, 12));
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);
        LocalDate from = LocalDate.of(2018, 1, 1);
        LocalDate to = LocalDate.of(2019, 1, 1);

        when(service.findAllInvoiceByDateRange(from, to)).thenReturn(invoices);

        MvcResult mvcResult = mockMvc.perform(get("/api/invoices")
            .param("from", from.toString())
            .param("to", to.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isOk())
            .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();
        Invoice[] mappedInvoices = objectMapper.readValue(jsonString, Invoice[].class);
        assertEquals(invoice, mappedInvoices[0]);
    }

    @Test
    @DisplayName("Should show internal server error")
    void shouldShowInternalServerError() throws Exception {

        when(service.findAllInvoices()).thenReturn(null);

        mockMvc.perform(get("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isInternalServerError());
    }

    @Test
    @DisplayName("Should return invoice")
    void shouldFindInvoice() throws Exception {
        Invoice invoice = prepareInvoice();
        invoice.setDate(LocalDate.of(2018, 12, 12));
        invoice.setId(1L);

        when(service.findInvoiceById(1L)).thenReturn(invoice);

        mockMvc.perform(get("/api/invoice/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.date", is("2018-12-12")));
    }

    @Test
    @DisplayName("Should show status not found invoice")
    void shouldDontFindInvoice() throws Exception {
        when(service.findInvoiceById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/invoice/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isNotFound());
    }

    @Test
    @DisplayName("Should save invoice and return URI")
    void shouldSaveAndReturnUri() throws Exception {
        Invoice invoice = prepareInvoice();
        Invoice saved = invoice;
        saved.setId(1L);

        when(service.saveInvoice(invoice)).thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/api/invoice")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isCreated())
            .andReturn();

        String jsonValue = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(saved), jsonValue);
    }

    @Test
    @DisplayName("Should save invoice and return URI2")
    void shouldSaveAndReturnUri2() throws Exception {
        Invoice invoice = prepareInvoice();
        invoice.setId(1L);

        when(service.saveInvoice(invoice)).thenReturn(invoice);

        mockMvc.perform(post("/api/invoice")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isCreated())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.buyer.name", is(invoice.getBuyer().getName())));
    }

    @Test
    @DisplayName("Should dont save invoice and show Error 500")
    void shouldDontSaveInvoice() throws Exception {
        Invoice invoice = prepareInvoice();

        when(service.saveInvoice(invoice)).thenReturn(null);

        mockMvc.perform(post("/api/invoice")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isInternalServerError());
    }

    @Test
    @DisplayName("Should edit the invoice")
    void shouldEditInvoice() throws Exception {
        Invoice invoice = prepareInvoice();
        invoice.setId(1L);

        Invoice edited = prepareInvoice();
        edited.setId(1L);

        when(service.findInvoiceById(1L)).thenReturn(invoice);
        when(service.saveInvoice(invoice)).thenReturn(edited);

        MvcResult mvcResult = mockMvc.perform(put("/api/invoice")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isOk())
            .andReturn();

        String jsonValue = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(edited), jsonValue);
    }

    @Test
    @DisplayName("Should not found invoice for edit")
    void shouldNotFoundInvoiceForEdit() throws Exception {
        Invoice invoice = prepareInvoice();
        invoice.setId(1L);

        when(service.findInvoiceById(1L)).thenReturn(null);

        mockMvc.perform(put("/api/invoice")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isNotFound());
    }

    @Test
    @DisplayName("Should not edit invoice")
    void shouldNotEditInvoice() throws Exception {
        Invoice invoice = prepareInvoice();
        invoice.setId(1L);

        when(service.findInvoiceById(1L)).thenReturn(invoice);
        invoice.setBuyer(prepareCompany("Poznan", "DDr"));
        when(service.saveInvoice(invoice)).thenReturn(null);

        mockMvc.perform(put("/api/invoice")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invoice))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isInternalServerError());
    }

    @Test
    @DisplayName("Should show no content")
    void shouldDeleteInvoice() throws Exception {
        Invoice invoice = prepareInvoice();
        invoice.setId(1L);

        when(service.deleteInvoiceById(invoice.getId())).thenReturn(invoice);

        mockMvc.perform(delete("/api/invoice/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isNoContent());
    }


    @Test
    @DisplayName("Should dont delete and show internal error")
    void shouldDontDeleteAndShowInternalError() throws Exception {
        Invoice invoice = prepareInvoice();
        invoice.setId(1L);

        when(service.deleteInvoiceById(invoice.getId())).thenReturn(null);

        mockMvc.perform(delete("/api/invoice/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status()
                .isInternalServerError());
    }

    private Invoice prepareInvoice() {
        Random random = new Random();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Company buyer = prepareCompany("Wrocław 66-666", "TurboMarek z.o.o");
        Company seller = prepareCompany("Gdynia 66-666", "Szczupak z.o.o");
        Invoice invoice = new Invoice();
        invoice.setDate(LocalDate.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1));
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        return invoice;
    }

    private Company prepareCompany(String city, String company) {
        Random random = new Random();
        return new Company(
            (long) (random.nextInt(10000) + 1),
            (random.nextInt(999999999) + 9999999) + "",
            city,
            company);
    }

    private List<Invoice> prepareInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice1 = prepareInvoice();
        invoices.add(invoice1);
        Invoice invoice2 = prepareInvoice();
        invoices.add(invoice2);
        Invoice invoice3 = prepareInvoice();
        invoices.add(invoice3);
        return invoices;
    }
}