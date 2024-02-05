package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.RegisterRequest;
import com.enigma.kingkost.dto.response.RegisterResponse;
import com.enigma.kingkost.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTH)
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/register/seller")
    public RegisterResponse registerSeller(@RequestBody RegisterRequest registerRequest) {
        return registerService.registerSeller(registerRequest);
    }

    @PostMapping("/register/customer")
    public RegisterResponse registerCustomer(@RequestBody RegisterRequest registerRequest) {
        return registerService.registerCustomer(registerRequest);
    }
}
