package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.AdminRequest;
import com.enigma.kingkost.dto.response.AdminResponse;

import java.util.List;

public interface AdminService {

    AdminResponse createAdmin(AdminRequest adminRequest);

    AdminResponse update(AdminRequest adminRequest);

    void deleteAdmin(String id);

    List<AdminResponse> getAll();

    AdminResponse getById(String id);
}
