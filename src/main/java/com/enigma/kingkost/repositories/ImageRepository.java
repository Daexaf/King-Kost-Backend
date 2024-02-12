package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    List<Image> findByKost_IdAndIsActiveTrue(String kostId);
}
