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
@Table(name = "m_image_kost")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "url_image")
    private String url;
    @JoinColumn(name = "kost_id")
    @ManyToOne
    private Kost kost;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "craeted_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
