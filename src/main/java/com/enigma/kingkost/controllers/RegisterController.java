package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.AdminRequest;
import com.enigma.kingkost.dto.request.RegisterRequest;
import com.enigma.kingkost.dto.response.AdminResponse;
import com.enigma.kingkost.dto.response.RegisterResponse;
import com.enigma.kingkost.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:5173")
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
    public RegisterResponse registerCustomer(@RequestBody RegisterRequest registerRequest, @RequestParam("profileImage")MultipartFile profileImage) {
        return registerService.registerCustomer(registerRequest, profileImage);
    }

    @PostMapping("/register/admin")
    public AdminResponse registerAdmin(@RequestBody AdminRequest adminRequest) {
        return registerService.registerAdmin(adminRequest);
    }
}
