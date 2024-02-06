package com.enigma.kingkost.services;
import com.enigma.kingkost.entities.PaymentType;

import java.util.List;

public interface PaymentService {

    PaymentType getOrSave(PaymentType payment);

    PaymentType getById(String id);

    List<PaymentType> getAll();

    PaymentType createPayment(PaymentType payment);
}
