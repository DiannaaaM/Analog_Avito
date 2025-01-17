package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.model.ImageEntity;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    ImageEntity save(ImageEntity image);
    ImageEntity findById(long id);
    List<ImageEntity> findAllByOrderByNameAsc();
    List<ImageEntity> getImagesByAdId(Long id);

    void delete(Optional<ImageEntity> image);

    ImageEntity uploadImage(MultipartFile imageFile);
}
