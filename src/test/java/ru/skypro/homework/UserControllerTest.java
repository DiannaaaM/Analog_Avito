package ru.skypro.homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.UpdatePasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.service.AvatarService;
import ru.skypro.homework.service.UserService;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private EntityMapper mapper;

    @Mock
    private AvatarService avatarService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void setPassword_ValidPassword_ReturnsCreated() throws Exception {
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setCurrentPassword("oldPassword");
        updatePasswordDTO.setNewPassword("newPassword123");

        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatePasswordDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Пароль успешно изменен"));
    }

    @Test
    void setPassword_InvalidPassword_ReturnsBadRequest() throws Exception {
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setCurrentPassword("oldPassword");
        updatePasswordDTO.setNewPassword("short");

        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatePasswordDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Пароль не проходит по требованиям. Длина пароля должна быть не менее 8 символов и не более 16!"));
    }

    @Test
    void getUserInfo_UserExists_ReturnsUserInfo() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testUser");

        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(mapper.userEntityToUserDTO(any(UserEntity.class))).thenReturn(userDTO);

        mockMvc.perform(get("/users/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void getUserInfo_UserNotFound_ReturnsNotFound() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);

        mockMvc.perform(get("/users/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserInfo_ValidData_ReturnsUpdatedUserId() throws Exception {
        UserDTO updateUser = new UserDTO();
        updateUser.setId(1L);
        updateUser.setUsername("updatedUser");

        when(userService.updateUserInfo(any(UserDTO.class))).thenReturn(1L);

        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void updateImage_ValidImage_ReturnsImageId() throws Exception {
        MultipartFile imageFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test data".getBytes());
        when(avatarService.uploadImage(any(MultipartFile.class))).thenReturn(new AvatarEntity());

        mockMvc.perform(patch("/users/me/image")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("image", "test.jpg")
                        .content("test data".getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().isOk());
    }

    @Test
    void uploadImage_ValidUserId_ReturnsSuccessMessage() throws Exception {
        MultipartFile imageFile = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "test data".getBytes());

        when(avatarService.uploadImage(any(MultipartFile.class))).thenReturn(new AvatarEntity());

        mockMvc.perform(multipart("/users/1/image")
                        .file( (MockMultipartFile) imageFile ))
                .andExpect(status().isOk())
                .andExpect(content().string("Image uploaded successfully"));
    }
}
