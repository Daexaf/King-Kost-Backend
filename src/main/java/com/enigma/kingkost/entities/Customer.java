package com.enigma.kingkost.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "m_customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fullName;
    private String email;
    @ManyToOne
    @JoinColumn(name = "gender_type_id")
    private GenderType genderTypeId;
    private String phoneNumber;
    private String address;
    @OneToOne
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;
    private String profileImageName;
    private String profileImageType;
    private String url;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
