package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.AdminRequest;
import com.enigma.kingkost.dto.response.AdminResponse;
import com.enigma.kingkost.entities.Admin;
import com.enigma.kingkost.repositories.AdminRepository;
import com.enigma.kingkost.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminResponse createAdmin(AdminRequest adminRequest) {
        Admin admin = Admin.builder()
                .username(adminRequest.getUsername())
                .password(adminRequest.getPassword())
                .roleTypeId(adminRequest.getRoleTypeId())
                .build();
        adminRepository.save(admin);
        return getAdminResponse(admin);
    }

    @Override
    public AdminResponse update(AdminRequest adminRequest) {
        Admin currentAdmin = adminRepository.findById(adminRequest.getId()).orElse(null);
        if (currentAdmin == null) {
            throw new NullPointerException("Current admin is null");
        }
        if (currentAdmin != null) {
            LocalDateTime createdAt = currentAdmin.getCreatedAt();
            currentAdmin.setUsername(adminRequest.getUsername());
            if (!passwordEncoder.matches(adminRequest.getPassword(), currentAdmin.getPassword())) {
                currentAdmin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
            } else {
                currentAdmin.setPassword(adminRequest.getPassword());
            }

            currentAdmin.setRoleTypeId(adminRequest.getRoleTypeId());
            currentAdmin.setCreatedAt(createdAt);
            currentAdmin.setActive(adminRequest.isActive());
            adminRepository.save(currentAdmin);
            return getAdminResponse(currentAdmin);
        }
        return null;
    }


    @Override
    public void deleteAdmin(String id) {
        adminRepository.deleteById(id);
    }

    @Override
    public List<AdminResponse> getAll() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(this::getAdminResponse).collect(Collectors.toList());
    }

    @Override
    public AdminResponse getById(String id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        assert admin != null;
        return getAdminResponse(admin);
    }

    private AdminResponse getAdminResponse(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .roleTypeId(admin.getRoleTypeId())
                .active(admin.isActive())
                .build();
    }
}
