package com.enigma.kingkost.services;
import com.enigma.kingkost.entities.PaymentType;

import java.util.List;

public interface PaymentService {

    void autoCreate();

    PaymentType getById(String id);

    List<PaymentType> getAll();

    PaymentType createPayment(PaymentType payment);
}
