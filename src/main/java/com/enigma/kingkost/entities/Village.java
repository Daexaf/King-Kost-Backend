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
@Table(name = "m_village")
public class Village {
    @Id
    private String id;
    @Column(nullable = false, length = 120, unique = true)
    private String name;
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "m_subdistrict")
    private Subdistrict subdistrict;
}
