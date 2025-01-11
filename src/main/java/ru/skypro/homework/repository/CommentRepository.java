package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    CommentEntity save(CreateOrUpdateCommentDTO comment);
    CommentEntity findById(long id);
    CommentEntity updateById(CreateOrUpdateCommentDTO comment);
    CommentEntity deleteById(long id);

}
