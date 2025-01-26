package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.model.ImageEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<AvatarEntity, Integer> {
    Optional<AvatarEntity> save(Optional<AvatarEntity> avatar);
    Optional<AvatarEntity> findById(long id);
    List<AvatarEntity> getImagesByUser(Long id);

}
