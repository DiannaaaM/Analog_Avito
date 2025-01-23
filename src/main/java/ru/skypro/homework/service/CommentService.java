package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    long newComment(long adId, CommentDTO commentDTO);
    void deleteComm(long adId, long commId);
    CommentDTO updateCommentInfo(long adId, long commentId, CommentDTO commentDTO);
    List<CommentDTO> showCommentToAd(long adId);
}
