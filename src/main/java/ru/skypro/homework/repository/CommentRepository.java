package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.CommentEntity;
import java.util.List;
import ru.skypro.homework.model.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByAdId(Long adId);
    void deleteByIdAndAdId(Long commentId, Long adId);
}