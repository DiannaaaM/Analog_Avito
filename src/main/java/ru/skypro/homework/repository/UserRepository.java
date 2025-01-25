package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.ImageDTO;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Modifying
    @Query("UPDATE UserEntity u SET u.avatar = :image WHERE u.id = :userId")
    void updateUserImage(@Param("image") AvatarEntity avatar, @Param("userId") Long userId);

    UserEntity findByFirstName(String firstName);

    boolean existsById(long id);

    UserEntity findById(long id);

    UserEntity findByUsername(String username);
}
