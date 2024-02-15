package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.entities.TransactionKost;
import com.enigma.kingkost.services.ApprovalService;
import com.enigma.kingkost.services.TransactionKostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    private final TransactionKostService transactionKostService;

    @Override
    public void approveTransactionKost(String transactionId, Integer approve, String sellerId) {
        TransactionKost findTransactionKost = transactionKostService.getById(transactionId);
        if (findTransactionKost.getAprStatus() > 0) {
            throw new NullPointerException("Transaction was approv");
        }
        if (!findTransactionKost.getKost().getSeller().getId().equals(sellerId)) {
            throw new NullPointerException("Cannont approve transaction");
        }
        transactionKostService.update(TransactionKost.builder()
                .id(findTransactionKost.getId())
                .kost(findTransactionKost.getKost())
                .customer(findTransactionKost.getCustomer())
                .monthType(findTransactionKost.getMonthType())
                .paymentType(findTransactionKost.getPaymentType())
                .aprStatus(approve)
                .createdAt(findTransactionKost.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build());
    }

}
