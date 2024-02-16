package com.enigma.kingkost.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EmailRequestCustomer {
    private String emailTo;
    private String subject;
    private String kostName;
    private String customerName;
    private String sellerName;
    private String emailSeller;
    private String noPhoneSeller;
    private String bookingDate;
    private String updateBookingDate;
    private String statusBooking;
}
