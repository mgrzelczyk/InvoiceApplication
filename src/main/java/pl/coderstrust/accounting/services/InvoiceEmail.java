package pl.coderstrust.accounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Async;
import pl.coderstrust.accounting.model.Invoice;

public class InvoiceEmail {

    private JavaMailSender emailSender;
    private MailProperties mailProperties;
    private InvoicePdfService invoicePdfService;
    
    @Autowired
    public InvoiceEmailImpl(JavaMailSender emailSender,
                            MailProperties mailProperties,
                            InvoicePdfService invoicePdfService){
        this.emailSender = emailSender;
        this.mailProperties = mailProperties;
        this.invoicePdfService = invoicePdfService;
    }

    @Async
    public void sendEmailWithInvoice(Invoice invoice){
        if (invoice == null){
            throw new IllegalArgumentException("Invoice can not be null.")
        }
        try {
            MineMessage message = emailSender.createMineMessage();
            MineMessageHelper helper = new MineMessageHelper(message, true);
            helper.setTo(mailProperties.getProperties().get("to"));
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject(mailProperties.getProperties().get("subject"));
            helper.setText(mailProperties.getProperties().get("content"));
            helper.addAttachment(String.format("%s.pdf", invoice.getId(),
                new ByteArrayResource(invoicePdfService.createPdf(invoice)));
            emailSender.send(message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
