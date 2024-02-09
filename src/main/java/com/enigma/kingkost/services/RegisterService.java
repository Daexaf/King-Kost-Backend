package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.AdminRequest;
import com.enigma.kingkost.dto.request.RegisterRequest;
import com.enigma.kingkost.dto.response.AdminResponse;
import com.enigma.kingkost.dto.response.RegisterResponse;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService {
    RegisterResponse registerCustomer(RegisterRequest registerRequest, MultipartFile profileImage);

    RegisterResponse registerSeller(RegisterRequest registerRequest);

    AdminResponse registerAdmin(AdminRequest adminRequest);
}
