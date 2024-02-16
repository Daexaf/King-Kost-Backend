package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.ForgotPasswordRequest;
import com.enigma.kingkost.dto.response.ForgotPasswordResponse;
import com.enigma.kingkost.entities.Customer;
import com.enigma.kingkost.entities.Seller;
import com.enigma.kingkost.entities.UserCredential;
import com.enigma.kingkost.repositories.CustomerRepository;
import com.enigma.kingkost.repositories.SellerRepository;
import com.enigma.kingkost.repositories.UserCredentialRepository;
import com.enigma.kingkost.services.ForgotEmailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ForgotEmailServiceImpl implements ForgotEmailService {

    private final JavaMailSender mailSender;
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;

    public ForgotEmailServiceImpl(JavaMailSender mailSender, UserCredentialRepository userCredentialRepository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository, SellerRepository sellerRepository) {
        this.mailSender = mailSender;
        this.userCredentialRepository = userCredentialRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public ForgotPasswordResponse forgotPasswordCustomer(ForgotPasswordRequest forgotPasswordRequest) {
        String newPassword = randomPasswordGenerator(8);
        Optional<Customer> optionalUser = customerRepository.findByEmail(forgotPasswordRequest.getEmail());

        if(optionalUser.isPresent()){
            Customer customer = optionalUser.get();
            UserCredential user = customer.getUserCredential();
            user.setPassword(passwordEncoder.encode(newPassword));
            userCredentialRepository.save(user);

            sendNewPassword(forgotPasswordRequest.getEmail(), newPassword);

            return ForgotPasswordResponse.builder()
                    .email(forgotPasswordRequest.getEmail())
                    .message("New Password sent to your email.")
                    .build();
        }else {
            throw new NullPointerException("user with email " + forgotPasswordRequest.getEmail() + " not found");
        }
    }

    @Override
    public ForgotPasswordResponse forgotPasswordSeller(ForgotPasswordRequest forgotPasswordRequest) {
        String newPassword = randomPasswordGenerator(8);
        Optional<Seller> optionalSeller = sellerRepository.findByEmail(forgotPasswordRequest.getEmail());

        if(optionalSeller.isPresent()){
            Seller seller = optionalSeller.get();
            UserCredential user = seller.getUserCredential();
            user.setPassword(passwordEncoder.encode(newPassword));
            userCredentialRepository.save(user);

            sendNewPassword(forgotPasswordRequest.getEmail(), newPassword);

            return ForgotPasswordResponse.builder()
                    .email(forgotPasswordRequest.getEmail())
                    .message("New Password sent to your email.")
                    .build();
        }else {
            throw new NullPointerException("user with email " + forgotPasswordRequest.getEmail() + " not found");
        }
    }

    @Override
    public void sendNewPassword(String email, String newPassword) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(new InternetAddress("truecuks19@gmail.com"));
            mimeMessageHelper.setSubject("Password Reset Confirmation");
            mimeMessageHelper.setText("Your new password has been successfully reset. Your updated password is: " + newPassword, true);
            mimeMessageHelper.setTo(email);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error sending email with new password.", e);
        }
    }

    @Override
    public String randomPasswordGenerator(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }
}
