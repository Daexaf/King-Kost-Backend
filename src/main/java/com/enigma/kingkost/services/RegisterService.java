package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.RegisterRequest;
import com.enigma.kingkost.dto.response.RegisterResponse;

public interface RegisterService {
    RegisterResponse registerCustomer(RegisterRequest registerRequest);

    RegisterResponse registerSeller(RegisterRequest registerRequest);
}
