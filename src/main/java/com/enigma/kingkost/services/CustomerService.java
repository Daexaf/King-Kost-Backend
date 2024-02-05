package com.enigma.kingkost.services;
import com.enigma.kingkost.dto.request.CustomerRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CustomerResponse update(CustomerRequest customerRequest);

    void deleteCustomer(String id);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getById(String id);
}
