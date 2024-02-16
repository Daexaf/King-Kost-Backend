package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.constant.HtmlContent;
import com.enigma.kingkost.dto.request.EmailRequestCustomer;
import com.enigma.kingkost.dto.request.EmailRequestSeller;
import com.enigma.kingkost.services.EmailService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendHtmlEmailForSeller(EmailRequestSeller emailRequestSeller) {
        String htmlContent = HtmlContent.HTML_CONTENT_SELLER.replace("[Nama Seller]", emailRequestSeller.getSellerName()).replace("[Nama Kost]", emailRequestSeller.getKostName()).replace("[Nama Pemesan]", emailRequestSeller.getCustomerName()).replace("[Email Pemesan]", emailRequestSeller.getCustomerEmail()).replace("[Nomor Telepon Pemesan]", emailRequestSeller.getPhoneCustomer()).replace("[Tanggal Pemesan]", emailRequestSeller.getBookingDate()).replace("[Tanggal Update Pemesan]", emailRequestSeller.getUpdateBookingDate()).replace("[Status Pemesan]", emailRequestSeller.getStatusBooking());

        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress("truecuks19@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRequestSeller.getEmailTo()));
            message.setSubject(emailRequestSeller.getSubject());
            message.setContent(htmlContent, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendHtmlEmailForCustomer(EmailRequestCustomer emailRequestCustomer) {
        String htmlContent = HtmlContent.HTML_CONTENT_CUSTOMER.replace("[Nama Customer]", emailRequestCustomer.getCustomerName()).replace("[Nama Kost]", emailRequestCustomer.getKostName()).replace("[Nama Pemilik Kost]", emailRequestCustomer.getSellerName()).replace("[Email Pemilik Kost]", emailRequestCustomer.getEmailSeller()).replace("[Nomor Telepon Pemilik Kost]", emailRequestCustomer.getNoPhoneSeller()).replace("[Tanggal Pemesan]", emailRequestCustomer.getBookingDate()).replace("[Status Pemesan]", emailRequestCustomer.getStatusBooking());

        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress("truecuks19@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRequestCustomer.getEmailTo()));
            message.setSubject(emailRequestCustomer.getSubject());
            message.setContent(htmlContent, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
