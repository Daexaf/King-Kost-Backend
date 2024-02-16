package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.EmailRequest;

public interface EmailService {
    void sendHtmlEmail(EmailRequest emailRequest);
}
