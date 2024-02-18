package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.LoginRequest;
import com.enigma.kingkost.dto.response.CommondResponse;
import com.enigma.kingkost.dto.response.LoginResponse;
import com.enigma.kingkost.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTH)
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommondResponse.<LoginResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Login")
                        .data(loginResponse)
                        .build());
    }
}
