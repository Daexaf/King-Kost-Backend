package com.enigma.kingkost.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EmailRequest {
    String emailTo;
    String subject;
    String sellerName;
    String customerName;
    String customerEmail;
    String phoneCustomer;
    String bookingDate;
    String statusBooking;
}
