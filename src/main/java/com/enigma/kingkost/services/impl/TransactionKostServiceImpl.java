package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.TransactionKostRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.dto.response.KostResponse;
import com.enigma.kingkost.entities.*;
import com.enigma.kingkost.mapper.KostMapper;
import com.enigma.kingkost.repositories.TransactionKostRepository;
import com.enigma.kingkost.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionKostServiceImpl implements TransactionKostService {
    private final TransactionKostRepository transactionKostRepository;
    private final KostService kostService;
    private final CustomerService customerService;
    private final MonthService monthService;
    private final PaymentService paymentService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TransactionKost create(TransactionKostRequest transactionKostRequest) {
        Kost findKost = kostService.getById(transactionKostRequest.getKostId());
        CustomerResponse findCustomer = customerService.getById(transactionKostRequest.getCustomerId());
        MonthType monthType = monthService.getById(transactionKostRequest.getMonthTypeId());
        PaymentType paymentType = paymentService.getById(transactionKostRequest.getPaymentTypeId());

        return transactionKostRepository.save(TransactionKost.builder()
                .kost(findKost)
                .customer(Customer.builder()
                        .id(findCustomer.getId())
                        .address(findCustomer.getAddress())
                        .fullName(findCustomer.getFullName())
                        .email(findCustomer.getEmail())
                        .genderTypeId(findCustomer.getGenderTypeId())
                        .phoneNumber(findCustomer.getPhoneNumber())
                        .build())
                .monthType(monthType)
                .paymentType(paymentType)
                .aprStatus(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

    }

    @Override
    public List<TransactionKost> getBySellerId(String sellerId) {
        List<TransactionKost> transactionKostList = transactionKostRepository.findByKostSellerId(sellerId);
        return transactionKostList;
    }

    @Override
    public List<TransactionKost> getByCustomerId(String customerId) {
        List<TransactionKost> transactionKostList = transactionKostRepository.findByCustomerId(customerId);
        return transactionKostList;
    }

    @Override
    public TransactionKost getById(String id) {
        TransactionKost transactionKost = transactionKostRepository.findById(id).orElse(null);
        if (transactionKost == null) {
            throw new NullPointerException("Transaction not found");
        }
        return transactionKost;
    }

    @Override
    public TransactionKost update(TransactionKost transactionKost) {
        return transactionKostRepository.save(transactionKost);
    }

}
