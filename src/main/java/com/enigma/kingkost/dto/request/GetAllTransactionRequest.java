package com.enigma.kingkost.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GetAllTransactionRequest {
    private Integer page;
    private Integer size;
    private String sellerId;
    private String customerId;
    private Integer aprovStatus;
    private String kostName;
}
