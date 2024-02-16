package com.enigma.kingkost.dto.response;

import com.enigma.kingkost.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ReviewResponse {
    private String id;
    private String message;
    private Customer customerId;
}
