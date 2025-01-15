package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.io.IOException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityMapper mapper;

    public Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserEntity registration(RegisterDTO register) {
        UserEntity user = new UserEntity();
        user.setUsername(register.getUsername());
        user.setPassword(register.getPassword());
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setPhone(register.getPhone());
        user.setRole(register.getRole());
        return userRepository.save(user);
    }

    public UserEntity getCurrentUser() {
        String currentUsername = authentication().getName();
        return userRepository.findByUsername(currentUsername);
    }

    public long updateUserInfo(UserDTO update) {
        UserEntity updatedUser = userRepository.findByUsername(authentication().getName());
        if (updatedUser == null) {
            throw new RuntimeException("User not found");
        }
        // Обновите поля пользователя
        updatedUser.setFirstName(update.getFirstName());
        updatedUser.setLastName(update.getLastName());
        updatedUser.setPhone(update.getPhone());
        // Обновите изображение, если есть
        if (update.getImage() != null) {
            updatedUser.setImage(update.getImage());
        }
        userRepository.save(updatedUser);
        return updatedUser.getId();
    }
}
