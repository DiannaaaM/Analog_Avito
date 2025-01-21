package ru.skypro.homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AdsController adsController;

    @Mock
    private AdService adService;

    @Mock
    private CommentService commentService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adsController).build();

        user = new UserEntity();
        user.setId(1L);
        user.setUsername("testUser");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void getAllAds_ReturnsListOfAds() throws Exception {
        AdDTO ad = new AdDTO();
        ad.setId(1L);
        ad.setTitle("Test Ad");
        when(adService.findAllAds()).thenReturn(Collections.singletonList(ad));

        mockMvc.perform(get("/ads")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Ad"))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void createNewAd_ReturnsCreatedAd() throws Exception {
        AdDTO newAd = new AdDTO();
        newAd.setTitle("New Ad");
        when(adService.createAd(any(AdDTO.class))).thenReturn(newAd);

        mockMvc.perform(post("/ads")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newAd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Ad"));
    }

    @Test
    void getAdById_ReturnsAd() throws Exception {
        AdDTO ad = new AdDTO();
        ad.setId(1L);
        ad.setTitle("Test Ad");
        when(adService.getAd(1L)).thenReturn(ad);

        mockMvc.perform(get("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Ad"));
    }

    @Test
    void deleteAd_Success() throws Exception {
        mockMvc.perform(delete("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(adService, times(1)).deleteAd(1L);
    }

    @Test
    void updateAd_Success() throws Exception {
        AdDTO updatedAd = new AdDTO();
        updatedAd.setId(1L);
        updatedAd.setTitle("Updated Test Ad");

        when(adService.updateInfoAd(anyLong(), any(AdDTO.class))).thenReturn(updatedAd);

        mockMvc.perform(patch("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedAd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Test Ad"));
    }

    @Test
    void getMineAds_ReturnsUserAds() throws Exception {
        AdDTO ad = new AdDTO();
        ad.setId(1L);
        ad.setTitle("User Ad");

        when(adService.findAdsOfUser(1L)).thenReturn(Collections.singletonList(ad));

        mockMvc.perform(get("/ads/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("User Ad"));
    }

    @Test
    void getComments_ReturnsListOfComments() throws Exception {
        CommentDTO comment = new CommentDTO();
        comment.setId(1L);
        comment.setText("Test Comment");

        when(commentService.showCommentToAd(1L)).thenReturn(Collections.singletonList(comment));

        mockMvc.perform(get("/ads/1/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Test Comment"));
    }

    @Test
    void addComment_ReturnsCommentId() throws Exception {
        CommentDTO newComment = new CommentDTO();
        newComment.setText("New Comment");
        when(commentService.newComment(anyLong(), any(CommentDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/ads/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newComment)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void deleteComment_Success() throws Exception {
        mockMvc.perform(delete("/ads/1/comments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(commentService, times(1)).deleteComm(1L, 1L);
    }

    @Test
    void updateCommentInfo_Success() throws Exception {
        CommentDTO updatedComment = new CommentDTO();
        updatedComment.setText("Updated Comment");

        mockMvc.perform(patch("/ads/1/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedComment)))
                .andExpect(status().isOk());

        verify(commentService, times(1)).updateCommentInfo(1L, 1L, updatedComment);
    }
}
