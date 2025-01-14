package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.ImageDTO;
import ru.skypro.homework.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity save(UserEntity user);

    UserEntity updateUser(UserEntity user);

    UserEntity updateUserImage(ImageDTO image);

    UserEntity findByFirstName(String firstName);

    boolean existsById(long id);

    UserEntity findById(long id);

    UserEntity findByUsername(String username);
}
