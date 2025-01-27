package ru.skypro.homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class YourUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public YourUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Optional<UserEntity>> userEntityOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("Пользователя с данным именем нет \n:/");
        }
        Optional<UserEntity> userEntity = userEntityOptional.get();
        return User.builder()
                .username(email)
                .password( userEntity.get().getPassword())
                .roles( userEntity.get().getRole().name())
                .build();
    }
}
