package com.enigma.kingkost.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "m_kost")
public class Kost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, name = "available_room")
    private Integer availableRoom;
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;
    @Column(nullable = false, name = "is_wifi")
    private Boolean isWifi;
    @Column(nullable = false, name = "is_ac")
    private Boolean isAc;
    @Column(nullable = false, name = "is_parking")
    private Boolean isParking;
    @ManyToOne
    @JoinColumn(name = "gender_type_id", nullable = false)
    private GenderType genderType;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "subdistrict_id")
    private Subdistrict subdistrict;
    @OneToMany(mappedBy = "kost")
    private List<KostPrice> kostPrices;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
