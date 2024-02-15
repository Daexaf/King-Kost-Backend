package com.enigma.kingkost.entities;

import com.enigma.kingkost.constant.EGender;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_gender")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class GenderType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private EGender name;

}
