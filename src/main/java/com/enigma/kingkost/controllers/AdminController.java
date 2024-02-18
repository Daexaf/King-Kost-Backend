package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.AdminRequest;
import com.enigma.kingkost.dto.response.AdminResponse;
import com.enigma.kingkost.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.ADMIN)
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/v1")
    public AdminResponse createAdm(AdminRequest adminRequest) {
        return adminService.createAdmin(adminRequest);
    }

    @GetMapping("/v1")
    public List<AdminResponse> getAll() {
        return adminService.getAll();
    }

    @GetMapping("/v1/{id}")
    public AdminResponse getAdmById(@PathVariable String id) {
        return adminService.getById(id);
    }

    @DeleteMapping("/v1/{id}")
    public void deleteAdm(@PathVariable String id) {
        adminService.deleteAdmin(id);
    }

    @PutMapping("/v1")
    public AdminResponse updateAdmin(@RequestBody AdminRequest adminRequest) {
        return adminService.update(adminRequest);
    }
}
