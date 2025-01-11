package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.model.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
    Ad save(Ad ad);
    Ad updateAd(CreateOrUpdateAd ad);
    Ad deleteById(long id);
    Ad findByTitle(String title);
}
