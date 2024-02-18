    package com.enigma.kingkost.repositories;

    import com.enigma.kingkost.entities.ImagesProfile;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface ImagesProfileRepository extends JpaRepository<ImagesProfile, String> {
    }
