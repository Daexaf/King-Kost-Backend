package com.enigma.kingkost.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private UserCredential user;

    private Date expiryDate;
}
