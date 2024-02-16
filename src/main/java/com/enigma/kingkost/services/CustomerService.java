package com.enigma.kingkost.services;
import com.enigma.kingkost.dto.request.CustomerRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.entities.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(Customer customer);

    CustomerResponse update(CustomerRequest customerRequest);

    void deleteCustomer(String id);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getById(String id);
    Customer getCustomerById(String id);

    CustomerResponse addOrUpdateProfileImageForCustomer(String customerId, MultipartFile profileImage) throws IOException;

    CustomerResponse getCustomerByUserCredentialId(String userCredentialId);

}
