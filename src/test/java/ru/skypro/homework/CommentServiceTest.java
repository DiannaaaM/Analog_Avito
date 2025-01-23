package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AdRepository adRepository;

    @Mock
    private EntityMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void newComment_ShouldReturnCommentId() {
        long adId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        AdEntity adEntity = new AdEntity();
        CommentEntity newComment = new CommentEntity();
        when(adRepository.findById(adId)).thenReturn(adEntity);
        when(mapper.CommentDTOToCommentEntity(commentDTO)).thenReturn(newComment);
        when(commentRepository.save(newComment)).thenReturn(newComment);

        long commentId = commentService.newComment(adId, commentDTO);

        assertEquals(newComment.getId(), commentId);
        verify(commentRepository).save(newComment);
    }

    @Test
    void deleteComm_ShouldDeleteComment() {
        long adId = 1L;
        long commId = 1L;

        commentService.deleteComm(adId, commId);

        verify(commentRepository).deleteByIdAndAdId(commId, adId);
    }

    @Test
    void updateCommentInfo_ShouldUpdateComment() {
        long adId = 1L;
        long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        CommentEntity existingComment = new CommentEntity();
        existingComment.setAd(new AdEntity());
        when(commentRepository.findById(commentId)).thenReturn( Optional.of(existingComment));
        when(commentRepository.save(existingComment)).thenReturn(existingComment);
        when(mapper.commentEntityToCommentDTO(existingComment)).thenReturn(commentDTO);

        CommentDTO updatedComment = commentService.updateCommentInfo(adId, commentId, commentDTO);

        assertNotNull(updatedComment);
        verify(commentRepository).save(existingComment);
    }
}
