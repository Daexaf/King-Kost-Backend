package com.enigma.kingkost.services.impl;
import com.enigma.kingkost.entities.PaymentType;
import com.enigma.kingkost.repositories.PaymentRepository;
import com.enigma.kingkost.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentType getOrSave(PaymentType payment) {
        Optional<PaymentType> optionalPayment = paymentRepository.findById(payment.getId());
        if (!optionalPayment.isEmpty()) {
            return optionalPayment.get();
        }
        return paymentRepository.save(payment);
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
