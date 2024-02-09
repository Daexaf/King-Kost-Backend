package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.CustomerRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.entities.Customer;
import com.enigma.kingkost.entities.GenderType;
import com.enigma.kingkost.entities.UserCredential;
import com.enigma.kingkost.repositories.CustomerRepository;
import com.enigma.kingkost.services.CustomerService;
import com.enigma.kingkost.services.GenderService;
import com.enigma.kingkost.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final GenderService genderService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerResponse createCustomer(Customer customerRequest) {

        Customer customer = customerRepository.save(customerRequest);
        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(customer.getId())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .fullName(customer.getFullName())
                .genderTypeId(customer.getGenderTypeId())
                .build();
        return customerResponse;
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        Customer currentCustomerId = customerRepository.findById(customerRequest.getId()).orElse(null);

        if (currentCustomerId != null) {
            UserCredential userCredential = currentCustomerId.getUserCredential();
            if (userCredential == null) {
                userCredential = new UserCredential();
            }
            userCredential.setUsername(customerRequest.getUsername());
            userCredential.setPassword(passwordEncoder.encode(customerRequest.getPassword()));

            userService.updateUserCredential(userCredential);

            GenderType gender = genderService.getById(customerRequest.getGenderTypeId());
            currentCustomerId.setFullName(customerRequest.getFullName());
            currentCustomerId.setAddress(customerRequest.getAddress());
            currentCustomerId.setEmail(customerRequest.getEmail());
            currentCustomerId.setPhoneNumber(customerRequest.getPhoneNumber());
            currentCustomerId.setGenderTypeId(gender);
            currentCustomerId.setUserCredential(userCredential);

            customerRepository.save(currentCustomerId);

            return getCustomerResponse(currentCustomerId);
        }

        return null;
    }


    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::getCustomerResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getById(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        assert customer != null;
        return getCustomerResponse(customer);
    }

    private CustomerResponse getCustomerResponse(Customer customer) {
        GenderType gender =  customer.getGenderTypeId();
        return CustomerResponse.builder()
                .id(customer.getId())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .fullName(customer.getFullName())
                .genderTypeId(gender)
                .build();
    }
}
