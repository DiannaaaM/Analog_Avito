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

    /**
     * Добавляет новый комментарий к объявлению с заданным идентификатором adId.
     *
     * @param adId Идентификатор объявления, к которому будет добавлен комментарий.
     * @param commentDTO Данные добавляемого комментария.
     * @return Идентификатор созданного комментария.
     */
    public long newComment(long adId, CommentDTO commentDTO) {
        Optional<AdEntity> adEntity = adRepository.findById( adId );

        CommentEntity newComm = mapper.сommentDTOToCommentEntity( commentDTO );
        newComm.setAd( adEntity.orElse( null ) );

        commentRepository.save( newComm );
        return newComm.getId();
    }

    /**
     * Удаляет комментарий с заданным commentId из объявления с заданным adId.
     *
     * @param adId Идентификатор объявления, из которого будет удалён комментарий.
     * @param commId Идентификатор удаляемого комментария.
     */
    public void deleteComm(long adId, long commId) {
        commentRepository.deleteByIdAndAdId( commId, adId );
    }

    /**
     * Обновляет комментарий с заданным commentId в объявлении с заданным adId.
     *
     * @param adId Идентификатор объявления, в котором находится комментарий.
     * @param commentId Идентификатор обновляемого комментария.
     * @param commentDTO Обновляемые данные комментария.
     * @return Обновляемые данные комментария.
     * @throws RuntimeException Если комментарий не относится к объявлению с указанным идентификатором adId.
     */
    public CommentDTO updateCommentInfo(long adId, long commentId, CommentDTO commentDTO) {
        Optional<CommentEntity> existingCommentOpt = commentRepository.findById( commentId );

        if (existingCommentOpt.isEmpty() || !existingCommentOpt.get().getAd().getId().equals( adId )) {
            throw new RuntimeException( "Comment does not belong to the ad with id: " + adId );
        }

        CommentEntity existingComment = existingCommentOpt.get();
        existingComment.setText( commentDTO.getText() );
        CommentEntity updatedComment = commentRepository.save( existingComment );

        return mapper.commentEntityToCommentDTO( updatedComment );
    }

    /**
     * Извлекает все комментарии, связанные с объявлением, с заданным идентификатором.
     *
     * @параметр AdID - идентификатор объявления, для которой будут получены комментарии.
     * @возвращает список данных комментариев, связанных с объявлением.
     */
    public List<CommentDTO> showCommentToAd(long adId) {
        return mapper.commentEntitiesToCommentDTOs( commentRepository.findByAdId( adId ) );
    }
}
