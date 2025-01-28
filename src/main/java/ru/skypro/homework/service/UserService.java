package ru.skypro.homework.service;

import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.UserEntity;

import java.util.Optional;


public interface UserService {
    UserEntity registration(RegisterDTO register);
    Optional<UserEntity> getCurrentUser();
    long updateUserInfo(UserDTO update);
}
