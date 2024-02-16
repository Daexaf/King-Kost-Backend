package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.constant.EStatus;
import com.enigma.kingkost.dto.request.EmailRequest;
import com.enigma.kingkost.dto.request.TransactionKostRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.entities.*;
import com.enigma.kingkost.repositories.TransactionKostRepository;
import com.enigma.kingkost.services.*;
import com.enigma.kingkost.util.DateFormat;
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
    private final EmailService emailService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TransactionKost create(TransactionKostRequest transactionKostRequest) {
        Kost findKost = kostService.getById(transactionKostRequest.getKostId());
        if (findKost.getAvailableRoom() <= 0) {
            throw new NullPointerException("Boarding rooms are not available");
        }
        kostService.ReduceItAvailableRoom(findKost);
        CustomerResponse findCustomer = customerService.getById(transactionKostRequest.getCustomerId());
        MonthType monthType = monthService.getById(transactionKostRequest.getMonthTypeId());
        PaymentType paymentType = paymentService.getById(transactionKostRequest.getPaymentTypeId());
        TransactionKost transactionKost = transactionKostRepository.save(TransactionKost.builder()
                .kost(Kost.builder()
                        .id(findKost.getId())
                        .build())
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
        emailService.sendHtmlEmail(EmailRequest.builder()
                .emailTo(findKost.getSeller().getEmail())
                .subject("King Kost Booking Notification")
                .sellerName(findKost.getSeller().getFullName())
                .customerName(findCustomer.getFullName())
                .customerEmail(findCustomer.getEmail())
                .phoneCustomer(findCustomer.getPhoneNumber())
                .bookingDate(DateFormat.dateStringFormatNow())
                .statusBooking(String.valueOf(EStatus.values()[transactionKost.getAprStatus()]))
                .build());
        return transactionKost;
    }

    @Override
    public List<TransactionKost> getBySellerId(String sellerId) {
        return transactionKostRepository.findByKostSellerIdOrderByCreatedAtDesc(sellerId);
    }

    @Override
    public List<TransactionKost> getByCustomerId(String customerId) {
        return transactionKostRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
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

    @Override
    public void cancelTransactionKost(String customerId, String transactionId) {
        TransactionKost findTransaction = getById(transactionId);
        CustomerResponse findCustomer = customerService.getById(customerId);
        kostService.addAvailableRoom(findTransaction.getKost());
        if (!findTransaction.getCustomer().getId().equals(findCustomer.getId())) {
            throw new NullPointerException("Cannot cancel transaction");
        }
        if (findTransaction.getAprStatus() > 0) {
            throw new NullPointerException("Transaction was cancel");
        }
        TransactionKost transactionKost = transactionKostRepository.save(TransactionKost.builder()
                .id(findTransaction.getId())
                .kost(findTransaction.getKost())
                .customer(Customer.builder()
                        .id(findCustomer.getId())
                        .fullName(findCustomer.getFullName())
                        .email(findCustomer.getEmail())
                        .genderTypeId(findCustomer.getGenderTypeId())
                        .phoneNumber(findCustomer.getPhoneNumber())
                        .address(findCustomer.getAddress())
                        .build())
                .monthType(findTransaction.getMonthType())
                .paymentType(findTransaction.getPaymentType())
                .aprStatus(1)
                .createdAt(findTransaction.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build());

        emailService.sendHtmlEmail(EmailRequest.builder()
                .emailTo(transactionKost.getKost().getSeller().getEmail())
                .subject("Pembatalan pemesanan kost")
                .sellerName(transactionKost.getKost().getSeller().getFullName())
                .customerName(transactionKost.getCustomer().getFullName())
                .customerEmail(transactionKost.getCustomer().getEmail())
                .phoneCustomer(transactionKost.getCustomer().getPhoneNumber())
                .bookingDate(DateFormat.dateStringFormatNow())
                .statusBooking(String.valueOf(EStatus.values()[transactionKost.getAprStatus()]))
                .build());
        emailService.sendHtmlEmail(EmailRequest.builder()
                .emailTo(transactionKost.getKost().getSeller().getEmail())
                .subject("Pembatalan pemesanan kost")
                .sellerName(transactionKost.getKost().getSeller().getFullName())
                .customerName(transactionKost.getCustomer().getFullName())
                .customerEmail(transactionKost.getCustomer().getEmail())
                .phoneCustomer(transactionKost.getCustomer().getPhoneNumber())
                .bookingDate(DateFormat.dateStringFormatNow())
                .statusBooking(String.valueOf(EStatus.values()[transactionKost.getAprStatus()]))
                .build());
    }

}
