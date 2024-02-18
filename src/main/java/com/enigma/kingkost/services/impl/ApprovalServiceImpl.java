package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.constant.EStatus;
import com.enigma.kingkost.dto.request.EmailRequestSeller;
import com.enigma.kingkost.entities.TransactionKost;
import com.enigma.kingkost.services.ApprovalService;
import com.enigma.kingkost.services.EmailService;
import com.enigma.kingkost.services.TransactionKostService;
import com.enigma.kingkost.util.DateFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    private final TransactionKostService transactionKostService;
    private final EmailService emailService;

    @Override
        public void approveTransactionKost(String transactionId, Integer approve, String sellerId) {
        TransactionKost findTransactionKost = transactionKostService.getById(transactionId);
        if (findTransactionKost.getAprStatus() > 0) {
            throw new NullPointerException("Transaction was approv");
        }
        if (!findTransactionKost.getKost().getSeller().getId().equals(sellerId)) {
            throw new NullPointerException("Cannont approve transaction");
        }
        TransactionKost transactionKost = transactionKostService.update(TransactionKost.builder()
                .id(findTransactionKost.getId())
                .kost(findTransactionKost.getKost())
                .customer(findTransactionKost.getCustomer())
                .monthType(findTransactionKost.getMonthType())
                .paymentType(findTransactionKost.getPaymentType())
                .aprStatus(approve)
                .createdAt(findTransactionKost.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build());

        emailService.sendHtmlEmailForSeller(EmailRequestSeller.builder()
                .emailTo(transactionKost.getKost().getSeller().getEmail())
                .subject("King Kost Booking Notification")
                .kostName(transactionKost.getKost().getName())
                .sellerName(transactionKost.getKost().getSeller().getFullName())
                .customerName(transactionKost.getCustomer().getFullName())
                .customerEmail(transactionKost.getCustomer().getEmail())
                .phoneCustomer(transactionKost.getCustomer().getPhoneNumber())
                .bookingDate(DateFormat.dateStringFormat(transactionKost.getCreatedAt()))
                .updateBookingDate(DateFormat.dateStringFormat(transactionKost.getUpdatedAt()))
                .statusBooking(String.valueOf(EStatus.values()[transactionKost.getAprStatus()]))
                .build());
    }

}
