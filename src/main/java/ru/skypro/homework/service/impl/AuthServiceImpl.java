package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

import java.util.Optional;

/**
 * Этот класс реализует интерфейс {@link AuthService}. Он предоставляет методы для аутентификации и регистрации пользователей.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    /**
     * Конструктор для класса AuthServiceImpl.
     *
     * @param manager - экземпляр UserDetailsManager для управления пользовательскими данными.
     * @param passwordEncoder - экземпляр PasswordEncoder для кодирования паролей.
     * @param userRepository - экземпляр UserRepository для взаимодействия с базой данных пользователей.
     */
    public AuthServiceImpl(UserDetailsManager manager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.manager = manager;
        this.encoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Проверяет подлинность пользователя по электронной почте и паролю.
     *
     * @param email    Электронная почта пользователя.
     * @param password Пароль пользователя.
     * @return True, если пользователь прошел проверку подлинности, false в противном случае.
     */
    @Override
    public boolean login(String email, String password) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        if (userEntity.isEmpty()) {
            return false;
        }

        return userEntity.filter(entity -> encoder.matches(password, entity.getPassword())).isPresent();
    }

    /**
     * Зарегистрировать нового пользователя, используя предоставленный регистрационный адрес.
     *
     * @param register регистрационный адрес, содержащий информацию о пользователе.
     * @return True, если пользователь успешно зарегистрирован, и значение false в противном случае.
     */
    @Override
    public boolean register(RegisterDTO register) {
        if (manager.userExists(register.getEmail())) {
            return false;
        }

        try {
            manager.createUser(
                    User.builder()
                            .passwordEncoder(this.encoder::encode)
                            .username(register.getEmail())
                            .password(register.getPassword())
                            .roles(register.getRole().name())
                            .build()
            );
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}