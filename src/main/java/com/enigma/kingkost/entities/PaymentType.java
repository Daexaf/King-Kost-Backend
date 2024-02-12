package com.enigma.kingkost.entities;

import com.enigma.kingkost.constant.EPayment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private EPayment name;
}
