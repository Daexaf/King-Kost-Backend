package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
