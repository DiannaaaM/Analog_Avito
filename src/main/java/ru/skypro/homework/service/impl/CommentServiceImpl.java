package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
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

    public CommentDTO updateCommentInfo(long adId, long commentId, CommentDTO commentDTO) {
        Optional<CommentEntity> existingCommentOpt = commentRepository.findById(commentId);

        if (existingCommentOpt.isEmpty() || !existingCommentOpt.get().getAd().getId().equals(adId)) {
            throw new RuntimeException("Comment does not belong to the ad with id: " + adId);
        }

        CommentEntity existingComment = existingCommentOpt.get();
        existingComment.setText(commentDTO.getText());
        CommentEntity updatedComment = commentRepository.save(existingComment);

        return mapper.commentEntityToCommentDTO(updatedComment);
    }


    public List<CommentDTO> showCommentToAd(long adId) {
        return mapper.commentEntitiesToCommentDTOs(commentRepository.findByAdId(adId));
    }
}
