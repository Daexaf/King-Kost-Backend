package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.CustomerRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.entities.Customer;
import com.enigma.kingkost.entities.GenderType;
import com.enigma.kingkost.entities.Images;
import com.enigma.kingkost.entities.UserCredential;
import com.enigma.kingkost.repositories.CustomerRepository;
import com.enigma.kingkost.services.CustomerService;
import com.enigma.kingkost.services.GenderService;
import com.enigma.kingkost.services.ImagesService;
import com.enigma.kingkost.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final GenderService genderService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ImagesService imagesService;

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

    @Override
    public CustomerResponse addOrUpdateProfileImageForCustomer(String customerId, MultipartFile profileImage) throws IOException {
        Images images = imagesService.store(profileImage);
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer != null) {
            customer.setProfileImageName(images.getName());
            customer.setProfileImageType(images.getType());
            customer.setProfileImageData(images.getData());
            Customer updatedCustomer = customerRepository.save(customer);
            return convertToResponse(updatedCustomer);
        }

        return null;
    }

    @Override
    public CustomerResponse getCustomerByUserCredentialId(String userCredentialId) {
        Customer findCustomer = customerRepository.findByUserCredentialId(userCredentialId).orElse(null);
        if (findCustomer == null) {
            throw new NullPointerException("Customer not found");
        }
        return CustomerResponse.builder()
                .id(findCustomer.getId())
                .username(findCustomer.getUserCredential().getUsername())
                .address(findCustomer.getAddress())
                .profileImageData(findCustomer.getProfileImageData())
                .profileImageType(findCustomer.getProfileImageType())
                .profileImageName(findCustomer.getProfileImageName())
                .phoneNumber(findCustomer.getPhoneNumber())
                .email(findCustomer.getEmail())
                .genderTypeId(findCustomer.getGenderTypeId())
                .fullName(findCustomer.getFullName())
                .build();
    }

    private CustomerResponse convertToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .genderTypeId(customer.getGenderTypeId())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .profileImageName(customer.getProfileImageName())
                .profileImageType(customer.getProfileImageType())
                .profileImageData(customer.getProfileImageData())
                .build();
    }

    private CustomerResponse getCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .genderTypeId(customer.getGenderTypeId())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .profileImageName(customer.getProfileImageName())
                .profileImageType(customer.getProfileImageType())
                .profileImageData(customer.getProfileImageData())
                .build();
    }

}
