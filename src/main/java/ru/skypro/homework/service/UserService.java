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

    public Authentication authentication(){
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
        UserEntity userEntity = mapper.userDTOToUserEntity(update);
        UserEntity updatedUser = userRepository.findByFirstName(userEntity.getFirstName());
        mapper.userEntityToUserDTO(userRepository.updateUser(updatedUser));
        return updatedUser.getId();
    }

    public long updateUserImage(MultipartFile image) {
        UserEntity user = userRepository.findByFirstName(authentication().getName());

        user.setImage(image);

        userRepository.save(user);

        return user.getId();
    }

    public void uploadImage(Long userId, MultipartFile imageFile) throws IOException {
        UserEntity user = userRepository.findById(userId);

        user.setImage(imageFile);
        userRepository.save(user);
    }

}