package com.enigma.kingkost.entities;

import com.enigma.kingkost.constant.EMonth;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_month")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class MonthType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private EMonth name;
}
