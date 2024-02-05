package com.enigma.kingkost.repositories;

import com.enigma.kingkost.constant.ERole;
import com.enigma.kingkost.entities.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleType, String> {

    Optional<RoleType> findByName(ERole name);
}
