package com.enigma.kingkost.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ResponseMessage {
    private String message;
}
