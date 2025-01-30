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

    /**
     * Регистрирует нового пользователя с указанными данными.
     *
     * @param register DTO, содержащий регистрационные данные пользователя.
     * @return Вновь созданный объект {@link UserEntity}.
     */
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

    /**
     * Получает текущего аутентифицированного пользователя.
     *
     * @return Необязательный параметр, содержащий текущего пользователя, если он найден, в противном случае — пустой необязательный параметр.
     */
    public Optional<UserEntity> getCurrentUser() {
        String name = authentication().getName();
        return userRepository.findByFirstName(name);
    }

    /**
     * Обновляет информацию о пользователе с помощью предоставленных данных.
     *
     * @param update DTO, содержащий обновленные данные о пользователе.
     * @return Идентификатор обновленного объекта {@link UserEntity}.
     * @throws RuntimeException Если пользователь не найден.
     */
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

    /**
     * Обновляет изображение пользователя с помощью предоставленной сущности {@link AvatarEntity}.
     *
     * @param userId Идентификатор пользователя.
     * @param avatar Сущность {@link AvatarEntity} для обновления изображения пользователя.
     */
    public void updateUserImage(Long userId, AvatarEntity avatar) {
        userRepository.updateUserImage(avatar, userId);
    }

    /**
     * Получает все объявления, опубликованные текущим пользователем.
     *
     * @return Список {@link AdDTO}, представляющих объявления пользователя.
     * @throws RuntimeException Если пользователь не найден.
     */
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

