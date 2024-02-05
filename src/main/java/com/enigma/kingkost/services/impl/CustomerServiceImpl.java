package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.CustomerRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.entities.Customer;
import com.enigma.kingkost.entities.GenderType;
import com.enigma.kingkost.repositories.CustomerRepository;
import com.enigma.kingkost.services.CustomerService;
import com.enigma.kingkost.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final GenderService genderService;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        GenderType gender = customerRequest.getGenderTypeId();

        Customer customer = Customer.builder()
                .id(customerRequest.getId())
                .fullName(customerRequest.getFullName())
                .address(customerRequest.getAddress())
                .email(customerRequest.getEmail())
                .phoneNumber(customerRequest.getPhoneNumber())
                .genderTypeId(gender)
                .userCredentialId(customerRequest.getUserCredentialId())
                .build();
        customerRepository.save(customer);
        return getCustomerResponse(customer);
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        Customer currentCustomerId = customerRepository.findById(customerRequest.getId()).orElse(null);
        if (currentCustomerId != null) {
            GenderType gender = genderService.getById(customerRequest.getGenderTypeId().getId()); // Ambil ID gender yang sesuai
            Customer customer = Customer.builder()
                    .id(customerRequest.getId())
                    .fullName(customerRequest.getFullName())
                    .address(customerRequest.getAddress())
                    .email(customerRequest.getEmail())
                    .phoneNumber(customerRequest.getPhoneNumber())
                    .genderTypeId(gender)
                    .build();
            customerRepository.save(customer);
            return getCustomerResponse(customer);
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
                .fullName(customer.getFullName())
                .address(customer.getAddress())
                .email(customer.getEmail())
                .genderTypeId(gender.getId())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}
