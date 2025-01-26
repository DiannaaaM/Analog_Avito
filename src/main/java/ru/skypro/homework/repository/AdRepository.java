package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.AdEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, Integer> {
    Optional<AdEntity> save(Optional<AdEntity> ad);
    Optional<AdEntity> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<AdEntity> findByOwnerId(long id);
}

