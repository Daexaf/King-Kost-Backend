package com.enigma.kingkost.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionKostRequest {
    @NotBlank(message = "kostId is required")
    private String kostId;
    @NotBlank(message = "customerId is required")
    private String customerId;
    @NotBlank(message = "monthTypeId is required")
    private String monthTypeId;
    @NotBlank(message = "paymentTypeId is required")
    private String paymentTypeId;
}
