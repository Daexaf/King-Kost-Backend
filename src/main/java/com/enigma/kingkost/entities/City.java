package com.enigma.kingkost.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "m_city")
public class City {
    @Id
    private String id;
    @Column(nullable = false, length = 120)
    private String name;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
}


