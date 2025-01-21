package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void registration_ShouldReturnUserEntity() {
        RegisterDTO registerDTO = new RegisterDTO();
        UserEntity user = new UserEntity();
        when(mapper.userDTOToUserEntity(registerDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        UserEntity registeredUser = userService.registration(registerDTO);

        assertNotNull(registeredUser);
        verify(userRepository).save(user);
    }

    @Test
    void getCurrentUser_ShouldReturnUserEntity() {
        UserEntity user = new UserEntity();
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        UserEntity currentUser = userService.getCurrentUser();

        assertNotNull(currentUser);
        verify(userRepository).findByUsername("testUser");
    }

    @Test
    void updateUserInfo_ShouldUpdateUser() {
        UserDTO updateDTO = new UserDTO();
        UserEntity user = new UserEntity();
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        long updatedUserId = userService.updateUserInfo(updateDTO);

        assertEquals(user.getId(), updatedUserId);
        verify(userRepository).save(user);
    }
}
