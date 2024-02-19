package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.ForgotPasswordRequest;
import com.enigma.kingkost.dto.response.ForgotPasswordResponse;

public interface ForgotEmailService {
    ForgotPasswordResponse forgotPasswordCustomer(ForgotPasswordRequest forgotPasswordRequest);

    ForgotPasswordResponse forgotPasswordSeller(ForgotPasswordRequest forgotPasswordRequest);

    void sendNewPassword(String email, String newPassword);

    String randomPasswordGenerator(int length);
}
