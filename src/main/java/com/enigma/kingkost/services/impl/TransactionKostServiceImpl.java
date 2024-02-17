package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.constant.EStatus;
import com.enigma.kingkost.dto.request.EmailRequestCustomer;
import com.enigma.kingkost.dto.request.EmailRequestSeller;
import com.enigma.kingkost.dto.request.GetAllTransactionRequest;
import com.enigma.kingkost.dto.request.TransactionKostRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.entities.*;
import com.enigma.kingkost.repositories.TransactionKostRepository;
import com.enigma.kingkost.services.*;
import com.enigma.kingkost.util.DateFormat;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        emailService.sendHtmlEmailForSeller(EmailRequestSeller.builder()
                .emailTo(findKost.getSeller().getEmail())
                .subject("King Kost Booking Notification")
                .kostName(findKost.getName())
                .sellerName(findKost.getSeller().getFullName())
                .customerName(findCustomer.getFullName())
                .customerEmail(findCustomer.getEmail())
                .phoneCustomer(findCustomer.getPhoneNumber())
                .bookingDate(DateFormat.dateStringFormat(transactionKost.getCreatedAt()))
                .updateBookingDate(DateFormat.dateStringFormat(transactionKost.getCreatedAt()))
                .statusBooking(String.valueOf(EStatus.values()[transactionKost.getAprStatus()]))
                .build());
        emailService.sendHtmlEmailForCustomer(EmailRequestCustomer.builder()
                .emailTo(transactionKost.getCustomer().getEmail())
                .subject("King Kost Booking Notification")
                .customerName(transactionKost.getCustomer().getFullName())
                .kostName(findKost.getName())
                .sellerName(findKost.getSeller().getFullName())
                .emailSeller(findKost.getSeller().getEmail())
                .noPhoneSeller(findKost.getSeller().getPhoneNumber())
                .bookingDate(DateFormat.dateStringFormatNow())
                .updateBookingDate(DateFormat.dateStringFormat(transactionKost.getCreatedAt()))
                .statusBooking(String.valueOf(EStatus.values()[transactionKost.getAprStatus()]))
                .build());
        return transactionKost;
    }

    @Override
    public Page<TransactionKost> getAllTransaction(GetAllTransactionRequest getAllTransactionRequest) {
        Specification<TransactionKost> specification = ((root, query, criteriaBuilder) -> {
            Join<TransactionKost, Kost> kostJoin = root.join("kost");
            Join<TransactionKost, Customer> customerJoin = root.join("customer");
            List<Predicate> predicates = new ArrayList<>();
            if (getAllTransactionRequest.getAprovStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("aprStatus"), getAllTransactionRequest.getAprovStatus()));
            }
            if (getAllTransactionRequest.getSellerId() != null) {
                predicates.add(criteriaBuilder.equal(kostJoin.get("seller").get("id"), getAllTransactionRequest.getSellerId()));
            }
            if (getAllTransactionRequest.getCustomerId() != null) {
                predicates.add(criteriaBuilder.equal(customerJoin.get("id"), getAllTransactionRequest.getCustomerId()));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });
        Pageable pageable = PageRequest.of(getAllTransactionRequest.getPage(), getAllTransactionRequest.getSize());
        Page<TransactionKost> transactionKosts = transactionKostRepository.findAll(specification, pageable);
        List<TransactionKost> transactionKostListResponse = new ArrayList<>();
        for (TransactionKost transactionKost : transactionKosts.getContent()) {
            transactionKostListResponse.add(transactionKost);
        }
        return new PageImpl<>(transactionKostListResponse, pageable, transactionKosts.getTotalElements());
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

        emailService.sendHtmlEmailForSeller(EmailRequestSeller.builder()
                .emailTo(transactionKost.getKost().getSeller().getEmail())
                .subject("King Kost Booking Notification")
                .kostName(transactionKost.getKost().getName())
                .sellerName(transactionKost.getKost().getSeller().getFullName())
                .customerName(findCustomer.getFullName())
                .customerEmail(findCustomer.getEmail())
                .phoneCustomer(findCustomer.getPhoneNumber())
                .bookingDate(DateFormat.dateStringFormat(transactionKost.getCreatedAt()))
                .updateBookingDate(DateFormat.dateStringFormat(transactionKost.getUpdatedAt()))
                .statusBooking(String.valueOf(EStatus.values()[transactionKost.getAprStatus()]))
                .build());
        emailService.sendHtmlEmailForCustomer(EmailRequestCustomer.builder()
                .emailTo(transactionKost.getCustomer().getEmail())
                .subject("King Kost Booking Notification")
                .customerName(transactionKost.getCustomer().getFullName())
                .kostName(transactionKost.getKost().getName())
                .sellerName(transactionKost.getKost().getSeller().getFullName())
                .emailSeller(transactionKost.getKost().getSeller().getEmail())
                .noPhoneSeller(transactionKost.getKost().getSeller().getPhoneNumber())
                .bookingDate(DateFormat.dateStringFormatNow())
                .updateBookingDate(DateFormat.dateStringFormat(transactionKost.getUpdatedAt()))
                .statusBooking(String.valueOf(EStatus.values()[transactionKost.getAprStatus()]))
                .build());
    }

}
