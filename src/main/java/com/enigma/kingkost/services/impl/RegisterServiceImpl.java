package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.constant.ERole;
import com.enigma.kingkost.dto.request.AdminRequest;
import com.enigma.kingkost.dto.request.RegisterRequest;
import com.enigma.kingkost.dto.response.AdminResponse;
import com.enigma.kingkost.dto.response.RegisterResponse;
import com.enigma.kingkost.entities.*;
import com.enigma.kingkost.repositories.UserCredentialRepository;
import com.enigma.kingkost.services.*;
import com.enigma.kingkost.util.ValidationUtil;
import com.google.api.gax.rpc.AlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final ValidationUtil validationUtil;
    private final SellerService sellerService;
    private final GenderService genderService;
    private final AdminService adminService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerCustomer(RegisterRequest request) {
        try {
            validationUtil.validate(request);
            System.out.println("Received Username: " + request.getFullName());

            if (request.getFullName() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full name cannot be null");
            }
            UserCredential findUserCredential = userCredentialRepository.findByUsername(request.getUsername()).orElse(null);
            if (findUserCredential != null && findUserCredential.getUsername().equals(request.getUsername())) {
                throw new NullPointerException("Username already use");
            }

            RoleType role = RoleType.builder()
                    .name(ERole.ROLE_CUSTOMER)
                    .build();
            RoleType roleSaved = roleService.getOrSave(role);

            GenderType gender = genderService.getById(request.getGenderTypeId());

            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roleTypeId(roleSaved)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Customer customer = Customer.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .address(request.getAddress())
                    .genderTypeId(gender)
                    .phoneNumber(request.getPhoneNumber())
                    .userCredential(userCredential)
                    .build();

            customerService.createCustomer(customer);
            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRoleTypeId().getName().toString())
                    .build();

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer already exist");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerSeller(RegisterRequest request) {
        try {
            validationUtil.validate(request);
            System.out.println("Received Username: " + request.getFullName());

            if (request.getFullName() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full name cannot be null");
            }

            UserCredential findUserCredential = userCredentialRepository.findByUsername(request.getUsername()).orElse(null);
            if (findUserCredential != null && findUserCredential.getUsername().equals(request.getUsername())) {
                throw new NullPointerException("Username already use");
            }

            RoleType role = RoleType.builder()
                    .name(ERole.ROLE_SELLER)
                    .build();
            RoleType roleSaved = roleService.getOrSave(role);

            GenderType gender = genderService.getById(request.getGenderTypeId());

            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roleTypeId(roleSaved)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Seller seller = Seller.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .address(request.getAddress())
                    .genderTypeId(gender)
                    .phoneNumber(request.getPhoneNumber())
                    .userCredential(userCredential)
                    .build();

            sellerService.createSeller(seller);
            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRoleTypeId().getName().toString())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seller already exist");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public AdminResponse registerAdmin(AdminRequest request) {
        try {
            validationUtil.validate(request);
            System.out.println("Received Username: " + request.getUsername());

            if (request.getUsername() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username cannot be null");
            }

            RoleType role = RoleType.builder()
                    .name(ERole.ROLE_ADMIN)
                    .build();
            RoleType roleSaved = roleService.getOrSave(role);

            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roleTypeId(roleSaved)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            AdminRequest admin = AdminRequest.builder()
                    .id(userCredential.getId())
                    .username(userCredential.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roleTypeId(userCredential.getRoleTypeId().getId())
                    .build();
            adminService.createAdmin(admin);

            return AdminResponse.builder()
                    .id(admin.getId())
                    .username(userCredential.getUsername())
                    .roleTypeId(roleSaved.getId())
                    .build();

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin already exists");
        }
    }
}
