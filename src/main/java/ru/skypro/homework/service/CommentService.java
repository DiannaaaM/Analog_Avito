package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.mapper.EntityMapper;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private EntityMapper mapper;

    public long newComment(long adId, CommentDTO commentDTO) {
        AdEntity adEntity = adRepository.findById(adId);

        CommentEntity newComm = mapper.CommentDTOToCommentEntity(commentDTO);
        newComm.setAd(adEntity);

        commentRepository.save(newComm);
        return newComm.getId();
    }

    public void deleteComm(long adId, long commId) {
        commentRepository.deleteByIdAndAdId(commId, adId);
    }

    public CommentEntity updateCommentInfo(long adId, long commentId, CommentDTO commentDTO) {
        CommentEntity existingComment = commentRepository.findById(commentId);

        if (!existingComment.getAd().getId().equals(adId)) {
            throw new RuntimeException("Comment does not belong to the ad with id: " + adId);
        }

        existingComment.setText(commentDTO.getText());
        return commentRepository.save(existingComment);
    }

    public List<CommentEntity> showCommentToAd(long adId) {
        return commentRepository.findByAdId(adId);
    }
}
