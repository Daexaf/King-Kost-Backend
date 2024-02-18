package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.constant.EPayment;
import com.enigma.kingkost.entities.PaymentType;
import com.enigma.kingkost.repositories.PaymentRepository;
import com.enigma.kingkost.services.PaymentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @PostConstruct
    @Override
    public void autoCreate() {
        List<PaymentType> paymentTypes = getAll();
        if (paymentTypes.isEmpty()) {
            Arrays.stream(EPayment.values()).forEach(ePayment -> paymentRepository.save(PaymentType.builder()
                    .name(ePayment)
                    .build()));
        }
    }

    @Override
    public PaymentType getById(String id) {
        PaymentType payment = paymentRepository.findById(id).orElse(null);
        if (payment == null) {
            throw new NullPointerException("Payment type not found");
        }
        return payment;
    }

    @Override
    public List<PaymentType> getAll() {
        List<PaymentType> payment = paymentRepository.findAll();
        return payment;
    }

    @Override
    public PaymentType createPayment(PaymentType payment) {
        return paymentRepository.save(payment);
    }
}
