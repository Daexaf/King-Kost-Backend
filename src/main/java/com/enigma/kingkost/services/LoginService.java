package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.LoginRequest;
import com.enigma.kingkost.dto.response.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest loginRequest);
}
