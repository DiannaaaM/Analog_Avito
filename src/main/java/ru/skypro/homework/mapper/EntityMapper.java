package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    @Mapping(target = "id", ignore = true)
    AdDTO adEntityToAdDTO(AdEntity adEntity);

    @Mapping(target = "id", ignore = true)
    AdEntity adDTOToAdEntity(AdDTO adDTO);

    List<AdDTO> adEntitiesToAdDTOs(List<AdEntity> adEntityList);

    @Mapping(target = "id", ignore = true)
    UserDTO userEntityToUserDTO(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    UserEntity userDTOToUserEntity(RegisterDTO userDTO);

    @Mapping(target = "id", ignore = true)
    CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity);

    @Mapping(target = "id", ignore = true)
    CommentEntity CommentDTOToCommentEntity(CommentDTO commentDTO);

    List<CommentDTO> commentEntitiesToCommentDTOs(List<CommentEntity> commentEntityList);

    @Mapping(target = "imageUrl", ignore = true)
    ImageDTO imageEntityToImageDTO(ImageEntity imageEntity);

    @Mapping(target = "imagePath", ignore = true)
    ImageEntity imageDTOToImageEntity(ImageDTO imageDTO);

    List<ImageDTO> imageEntitiesToImageDTOs(List<ImageEntity> imageEntityList);

    default MultipartFile map(AvatarEntity value) {
        return null; // Placeholder implementation
    }
}
