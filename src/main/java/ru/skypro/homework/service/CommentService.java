package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public CommentEntity newComment(CreateOrUpdateCommentDTO comment){
        CommentEntity newComm = new CommentEntity();
        newComm.setAd(comment.getAd());
        newComm.setAuthor(comment.getAuthor());
        newComm.setText(comment.getText());
        newComm.setCreatedAt(comment.getCreatedAt());
        return commentRepository.save(newComm);
    }

    public CommentEntity deleteComm(long id) {
        return commentRepository.deleteById(id);
    }

    public CommentEntity updateComm(CreateOrUpdateCommentDTO comment){
        return commentRepository.updateById(comment);
    }
}
