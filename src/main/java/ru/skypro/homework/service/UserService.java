package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateImageDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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

    public UserEntity updateUserInfo(UpdateUserDTO update) {
        UserEntity updatedUser = userRepository.findByFirstName(update.getFirstName());
        return userRepository.updateUser(updatedUser);
    }

    public UserEntity updateUserImage(CreateOrUpdateImageDTO image) {
        return userRepository.updateUserImage(image);
    }
}
