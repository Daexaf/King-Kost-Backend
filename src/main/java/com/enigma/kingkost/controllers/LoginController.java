package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.LoginRequest;
import com.enigma.kingkost.dto.response.LoginResponse;
import com.enigma.kingkost.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTH)
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }
}
