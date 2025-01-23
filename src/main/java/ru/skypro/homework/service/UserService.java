package ru.skypro.homework.service;

import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.UserEntity;


public interface UserService {
    UserEntity registration(RegisterDTO register);
    UserEntity getCurrentUser();
    long updateUserInfo(UserDTO update);
}
