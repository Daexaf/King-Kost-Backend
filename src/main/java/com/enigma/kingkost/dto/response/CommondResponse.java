package com.enigma.kingkost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommondResponse<T>{
    private Integer statusCode;
    private String message;
    private T data;
}
