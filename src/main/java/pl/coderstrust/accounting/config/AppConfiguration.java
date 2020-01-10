package pl.coderstrust.accounting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import pl.coderstrust.accounting.mapper.InvoiceMapper;
import pl.coderstrust.accounting.repositories.file.FileHelper;
import pl.coderstrust.accounting.repositories.file.InFileDatabase;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class AppConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
            .registerModule(new JavaTimeModule());
    }

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ISO_DATE.format(object);
            }
        };
    }

    @Bean
    public InvoiceMapper invoiceMapper() {
        return Mappers.getMapper(InvoiceMapper.class);
    }

    @Bean
    @ConditionalOnProperty(name = "database", havingValue = "in-file")
    public InFileDatabase inFileDatabase() throws IOException {
        FileHelper fileHelper = new FileHelper("database.db");
        return new InFileDatabase(fileHelper, objectMapper);
    }

}
