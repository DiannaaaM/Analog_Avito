package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.AvatarEntity;

import java.util.List;

@Repository
public interface AvatarRepository extends JpaRepository<AvatarEntity, Integer> {
    AvatarEntity save(AvatarEntity avatar);
    AvatarEntity findById(long id);
    List<AvatarEntity> findAllByOrderByNameAsc();
    List<AvatarEntity> getImagesByAdId(Long id);
}
