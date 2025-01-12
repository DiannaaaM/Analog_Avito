package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;

@Mapper(componentModel = "spring")
public interface EntityMapper {
    AdDTO adEntityToAdDTO(AdEntity adEntity);
    AdEntity adDTOToAdEntity(AdDTO adDTO);

    UserDTO userEntityToUserDTO(UserEntity userEntity);
    UserEntity userDTOToUserEntity(UserDTO userDTO);

    CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity);
    CommentEntity CommentDTOToCommentEntity(CommentDTO commentDTO);

}
