package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.CreateOrUpdateImage;
import ru.skypro.homework.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User save(User user);

    User updateUser(User user);

    User updateUserImage(CreateOrUpdateImage image);

    User findByFirstName(String firstName);
}
