package com.enigma.kingkost.dto.response;

import com.enigma.kingkost.entities.MonthType;
import com.enigma.kingkost.entities.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionKostResponse {
    private String id;
    private KostResponse kost;
    private CustomerResponse customer;
    private MonthType monthType;
    private PaymentType paymentType;
    private Integer aprStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
