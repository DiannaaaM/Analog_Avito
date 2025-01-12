package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.CommentEntity;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    CommentEntity save(CommentDTO comment);

    CommentEntity findById(long id);

    CommentEntity updateById(CommentDTO comment);

    CommentEntity deleteByIdAndAdId(long commId, long adId);

    boolean existsById(long id);
    List<CommentEntity> findAll();
    List<CommentEntity> findByAdId(long ig);



}
