package com.enigma.kingkost.services;
import com.enigma.kingkost.dto.request.CustomerRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.entities.Customer;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(Customer customer);

    CustomerResponse update(CustomerRequest customerRequest);

    void deleteCustomer(String id);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getById(String id);
}
