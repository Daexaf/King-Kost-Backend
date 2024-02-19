package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.ForgotPasswordRequest;
import com.enigma.kingkost.dto.response.ForgotPasswordResponse;
import com.enigma.kingkost.services.ForgotEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.RESET_PASSWORD)
public class ForgotPasswordController {
        private final ForgotEmailService forgotEmailService;

    @PostMapping("/request-customer")
    public ForgotPasswordResponse resetPasswordCust(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return forgotEmailService.forgotPasswordCustomer(forgotPasswordRequest);
    }

    @PostMapping("/request-seller")
    public ForgotPasswordResponse resetPasswordSeller(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return forgotEmailService.forgotPasswordSeller(forgotPasswordRequest);
    }
}
