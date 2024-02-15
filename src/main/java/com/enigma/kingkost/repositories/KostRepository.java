package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.Kost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;


@EnableJpaRepositories
public interface KostRepository extends JpaRepository<Kost, String>, JpaSpecificationExecutor<Kost> {
    List<Kost> findKostBySellerId(String id);
}
