package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityMapper {
    AdDTO adEntityToAdDTO(AdEntity adEntity);
    AdEntity adDTOToAdEntity(AdDTO adDTO);
    List<AdDTO> adEntitiesToAdDTOs(List<AdEntity> adEntityList);

    UserDTO userEntityToUserDTO(UserEntity userEntity);
    UserEntity userDTOToUserEntity(RegisterDTO userDTO);

    CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity);
    CommentEntity CommentDTOToCommentEntity(CommentDTO commentDTO);
    List<CommentDTO>commentEntitiesToCommentDTOs(List<CommentEntity> commentEntityList);

    ImageDTO imageEntityToImageDTO(ImageEntity imageEntity);
    ImageEntity imageDTOToImageEntity(ImageDTO imageDTO);
    List<ImageDTO> imageEntitiesToImageDTOs(List<ImageEntity> imageEntityList);
}
