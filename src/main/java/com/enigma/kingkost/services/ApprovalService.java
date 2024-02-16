package com.enigma.kingkost.services;

public interface ApprovalService {
    void approveTransactionKost(String transactionId, Integer approve, String sellerId);
}
