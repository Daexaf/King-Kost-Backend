    package com.enigma.kingkost.repositories;

    import com.enigma.kingkost.entities.Images;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.Optional;

    @Repository
    public interface ImagesRepository extends JpaRepository<Images, String> {
        Optional<Images> findByName(String name);
    }
