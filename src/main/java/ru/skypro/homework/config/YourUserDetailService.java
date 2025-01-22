package ru.skypro.homework.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

@Service
public class YourUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public YourUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("Пользователя с данным именем нет \n:/");
        }
        UserEntity userEntity = userEntityOptional.get();
        return User.builder()
                .username(username)
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name())
                .build();
    }
}
