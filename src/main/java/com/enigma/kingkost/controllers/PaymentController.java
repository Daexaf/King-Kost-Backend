package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.entities.PaymentType;
import com.enigma.kingkost.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.PAYMENT)
public class PaymentController {

    private final PaymentService paymentService;
    @GetMapping(path="/v1")
    List<PaymentType> getPayment(){
        return paymentService.getAll();
    }
    @GetMapping(path = "/v1/{id}")
    PaymentType getPaymentById(@PathVariable String id) {
        return paymentService.getById(id);
    }
    @PostMapping(path = "/v1")
    PaymentType createPayment(@RequestBody PaymentType payment) {
        return paymentService.createPayment(payment);
    }
}
