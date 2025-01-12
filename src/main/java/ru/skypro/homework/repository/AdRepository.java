package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.model.AdEntity;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, Integer> {
    AdEntity save(AdEntity ad);
    AdEntity updateAd(AdDTO ad);
    AdEntity deleteById(long id);
    AdEntity findByTitle(String title);
    AdEntity findById(long id);
    boolean existsById(long id);
    List<AdEntity> findAll();
    List<AdEntity> findByOwnerId(long id);
}
