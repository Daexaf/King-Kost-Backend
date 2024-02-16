package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.constant.HtmlContent;
import com.enigma.kingkost.dto.request.EmailRequest;
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
    public void sendHtmlEmail(EmailRequest emailRequest) {
        String htmlContent = HtmlContent.HTML_CONTENT_SELLER.replace("[Nama Seller]", emailRequest.getSellerName()).replace("[Nama Pemesan]", emailRequest.getCustomerName()).replace("[Email Pemesan]", emailRequest.getCustomerEmail()).replace("[Nomor Telepon Pemesan]", emailRequest.getPhoneCustomer()).replace("[Tanggal Pemesan]", emailRequest.getBookingDate()).replace("[Status Pemesan]", emailRequest.getStatusBooking());

        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress("truecuks19@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRequest.getEmailTo()));
            message.setSubject(emailRequest.getSubject());
            message.setContent(htmlContent, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
