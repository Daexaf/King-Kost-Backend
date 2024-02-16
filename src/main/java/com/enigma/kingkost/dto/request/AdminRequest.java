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
public class AdminRequest {
    private String id;
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    private String roleTypeId;
    private boolean active;
}
