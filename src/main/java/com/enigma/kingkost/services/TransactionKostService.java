package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.TransactionKostRequest;
import com.enigma.kingkost.entities.TransactionKost;

import java.util.List;

public interface TransactionKostService {
    TransactionKost create(TransactionKostRequest transactionKostRequest);
    List<TransactionKost> getBySellerId(String sellerId);
    List<TransactionKost> getByCustomerId(String customerId);
    TransactionKost getById(String id);
    TransactionKost update(TransactionKost transactionKost);
    void cancelTransactionKost(String customerId, String transactionId);
}
