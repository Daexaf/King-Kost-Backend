package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.EmailRequestSeller;
import com.enigma.kingkost.dto.request.EmailRequestCustomer;

public interface EmailService {
    void sendHtmlEmailForSeller(EmailRequestSeller emailRequestSeller);
    void sendHtmlEmailForCustomer(EmailRequestCustomer emailRequestCustomer);
}
