package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityMapper mapper;

    @Autowired
    private AdRepository adRepository;

    public Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserEntity registration(RegisterDTO register) {
        UserEntity user = new UserEntity();
        user.setEmail(register.getEmail());
        user.setPassword(register.getPassword());
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setPhone(register.getPhone());
        user.setRole(register.getRole());
        return userRepository.save(user);
    }

    public Optional<UserEntity> getCurrentUser() {
        String name = authentication().getName();
        return userRepository.findByFirstName(name);
    }

    public long updateUserInfo(UserDTO update) {
        Optional<UserEntity> updatedUserOptional = userRepository.findByFirstName(authentication().getName());
        if (updatedUserOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        UserEntity updatedUser = updatedUserOptional.get();
        updatedUser.setFirstName(update.getFirstName());
        updatedUser.setLastName(update.getLastName());
        updatedUser.setPhone(update.getPhone());
        if (update.getAvatar() != null) {
            updatedUser.setAvatar((AvatarEntity) update.getAvatar());
        }
        userRepository.save(updatedUser);
        return updatedUser.getId();
    }

    public void updateUserImage(Long userId, AvatarEntity avatar) {
        userRepository.updateUserImage(avatar, userId);
    }

    public List<AdDTO> getUserAds() {
        Optional<UserEntity> currentUserOptional = getCurrentUser();
        if (currentUserOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        UserEntity currentUser = currentUserOptional.get();
        List<AdDTO> userAds = adRepository.findByOwnerId(currentUser.getId())
                .stream()
                .map(mapper::adEntityToAdDTO)
                .collect( Collectors.toList());
        return userAds;
    }
}

