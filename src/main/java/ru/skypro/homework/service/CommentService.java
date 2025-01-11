package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment newComment(CreateOrUpdateComment comment){
        Comment newComm = new Comment();
        newComm.setAd(comment.getAd());
        newComm.setAuthor(comment.getAuthor());
        newComm.setText(comment.getText());
        newComm.setCreatedAt(comment.getCreatedAt());
        return commentRepository.save(newComm);
    }

    public Comment deleteComm(long id) {
        return commentRepository.deleteById(id);
    }

    public Comment updateComm(CreateOrUpdateComment comment){
        return commentRepository.updateById(comment);
    }
}
