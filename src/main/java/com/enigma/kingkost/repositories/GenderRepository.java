package com.enigma.kingkost.repositories;
import com.enigma.kingkost.constant.EGender;
import com.enigma.kingkost.entities.GenderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<GenderType, String> {

}
