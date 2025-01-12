package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateImageDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityMapper mapper;

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

    public long updateUserInfo(UserDTO update) {
        UserEntity userEntity = mapper.userDTOToUserEntity(update);
        UserEntity updatedUser = userRepository.findByFirstName(userEntity.getFirstName());
        mapper.userEntityToUserDTO(userRepository.updateUser(updatedUser));
        return updatedUser.getId();
    }

    public long updateUserImage(CreateOrUpdateImageDTO image) {
        UserEntity user = userRepository.findById(image.getUserId());

        user.setImage(image.getImageUrl());

        userRepository.save(user);

        return user.getId();
    }

}