package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.CustomerRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CUSTOMER)
public class CustomerController {
    private final CustomerService customerService;

//    @PostMapping("/v1")
//    public CustomerResponse createCust(Cus customerRequest) {
//        return customerService.createCustomer(customerRequest);
//    }

    @GetMapping("/v1")
    public List<CustomerResponse> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/v1/{id}")
    public CustomerResponse getCustomerById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @DeleteMapping("/v1/{id}")
    public void deleteCust(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }

    @PutMapping("/v1")
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.update(customerRequest);
    }
}
