package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.GetAllTransactionRequest;
import com.enigma.kingkost.dto.request.TransactionKostRequest;
import com.enigma.kingkost.entities.TransactionKost;
import org.springframework.data.domain.Page;

public interface TransactionKostService {
    void create(TransactionKostRequest transactionKostRequest);

    Page<TransactionKost> getAllTransaction(GetAllTransactionRequest getAllTransactionRequest);

    TransactionKost getById(String id);

    TransactionKost update(TransactionKost transactionKost);

    TransactionKost getByKostId(String kostId);

    void cancelTransactionKost(String customerId, String transactionId);
}
