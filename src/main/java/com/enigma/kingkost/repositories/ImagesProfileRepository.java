    package com.enigma.kingkost.repositories;

    import com.enigma.kingkost.entities.ImagesProfile;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.Optional;

    @Repository
    public interface ImagesProfileRepository extends JpaRepository<ImagesProfile, String> {
        Optional<ImagesProfile> findByName(String name);
    }
