package com.bogovick.family.backend.image.facade.mapper;

import com.bogovick.family.backend.image.api.model.ImageContentDto;
import com.bogovick.family.backend.image.api.model.ImageContentType;
import com.bogovick.family.backend.image.core.entity.ImageContentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageContentMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(source = "name", target = "name")
  @Mapping(source = "contentType", target = "contentType")
  @Mapping(source = "size", target = "size")
  @Mapping(source = "filePath", target = "filePath")
  ImageContentEntity toEntity(ImageContentDto dto);

  ImageContentDto toDto(ImageContentEntity entity);

  default ImageContentEntity multipartFileToEntity(String fileName, MultipartFile file, String filePath, ImageContentType imageContentType,
                                                   long profileId, int order) {
    if (file == null) {
      return null;
    }

    ImageContentEntity entity = new ImageContentEntity();
    entity.setName(fileName);
    entity.setContentType(file.getContentType());
    entity.setSize(file.getSize());
    entity.setFilePath(filePath);
    entity.setImageContentType(imageContentType);
    entity.setProfileCardId(profileId);
    entity.setImageOrder(order);
    return entity;
  }
}
