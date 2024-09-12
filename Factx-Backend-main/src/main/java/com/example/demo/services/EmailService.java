package com.example.demo.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.activation.DataSource;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithAttachments(String toEmail, byte[] pdfFile, String xmlContent, String claveAcceso)
            throws jakarta.mail.MessagingException, IOException {

        jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("kevinganan2901@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject("Factura Electrónica");
        helper.setText("Factura Electrónica", true);

        // Adjuntar PDF
        DataSource pdfDataSource = new ByteArrayDataSource(pdfFile, "application/pdf");
        helper.addAttachment(claveAcceso + ".pdf", pdfDataSource);

        // Adjuntar XML
        helper.addAttachment(claveAcceso + ".xml", new ByteArrayResource(xmlContent.getBytes()));

        mailSender.send(message);
    }
}
