package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

    @Query("UPDATE ImageEntity i SET i.imagePath = :imagePath WHERE i.id = :imageId")
    @Modifying
    void updateImage(@Param("imagePath") String imagePath, @Param("imageId") Integer imageId);

    @Query("SELECT i FROM ImageEntity i WHERE i.ad.id = :adId")
    List<ImageEntity> findByAdId(@Param("adId") Integer adId);

    boolean existsById(Integer id);

    Optional<ImageEntity> findById(Integer id);

    @Modifying
    @Query("UPDATE ImageEntity i SET i.imagePath = :imagePath WHERE i.id = :imageId")
    void uploadImage(@Param("imagePath") String imagePath, @Param("imageId") Integer imageId);

    List<ImageEntity> getImagesByAdId(Long id);
}
