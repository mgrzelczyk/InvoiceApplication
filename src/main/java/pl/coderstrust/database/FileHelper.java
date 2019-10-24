package pl.coderstrust.accounting.database;

import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

    private static final String DATABASE_FILE_NAME = "database.db";

    public void readInvoiceFromFile(){

        InMemoryDatabase inMemoryDatabase = new InMemoryDatabase();
        inMemoryDatabase.invoiceMap;

        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(DATABASE_FILE_NAME))){
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line).append("\n");
                String[] parsedLine = line.split("|");
                Invoice i = new Invoice();
                i.setId(Long.parseLong(parsedLine[0]));
                invoiceMap.put(i.getId(), 1);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public void writeInvoiceToFile(){

        StringBuffer stringBuffer = new StringBuffer();
        for (Invoice invoice: Invoice.entries()){
            stringBuffer.append((invoice.getId()))
                    .append("|")
                    .append(invoice.getId())
                    .append("|")
                    .append(invoice.getEntries().size())
                    .append("\n");
        }

        Invoice invoice = new Invoice();

        try(FileWriter writer = new FileWriter(DATABASE_FILE_NAME);
            BufferedWriter bw = new BufferedWriter(writer)){
            bw.write(String.valueOf(invoice));
        } catch (IOException e) {
            System.err.format("IOException %s%n", e);
        }
    }

}
