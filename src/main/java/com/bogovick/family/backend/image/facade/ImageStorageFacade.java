package com.bogovick.family.backend.image.facade;

import com.bogovick.family.backend.image.api.model.ImageContentDto;
import com.bogovick.family.backend.image.api.model.ImageContentType;
import com.bogovick.family.backend.image.core.entity.ImageContentEntity;
import com.bogovick.family.backend.image.facade.mapper.ImageContentMapper;
import com.bogovick.family.backend.image.service.FilePathGenerator;
import com.bogovick.family.backend.image.service.FileUtil;
import com.bogovick.family.backend.image.service.ImageStoreContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.bogovick.family.backend.image.service.FilePathGenerator.generateFilePath;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageStorageFacade {
  private final ImageStoreContentService imageStoreContentService;
  private final ImageContentMapper imageContentMapper;
  private final FileUtil fileUtil;

  private static final ExecutorService contentService = Executors.newVirtualThreadPerTaskExecutor();

  public Future<ImageContentDto> saveContent(
      final MultipartFile file,
      final ImageContentType imageContentType,
      final long profileId,
      final int order) {
    if (file == null || imageContentType == null || profileId <= 0) {
      throw new RuntimeException("file and imageContent and profileId must be a set");
    }
    final String fileName = FilePathGenerator.getUniqueFileName(file);
    return contentService.submit(() -> {
      val path = generateFilePath(imageContentType, profileId);
      final Path realPathForSave = fileUtil.getRealPathForSave(path);
      try {
        Files.copy(file.getInputStream(), realPathForSave.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        log.error("error during save image content {} to path {}", file.getName(), path, e);
        return null;
      }
      final ImageContentEntity imageContentEntity =
          imageContentMapper.multipartFileToEntity(fileName,
              file, path.toString(), imageContentType, profileId, order);
      try {
        return Optional.ofNullable(imageStoreContentService.save(imageContentEntity))
            .map(imageContentMapper::toDto).orElse(null);
      } catch (Exception e) {
        log.error("error during sve to db image content {}", imageContentEntity);
        Files.deleteIfExists(realPathForSave);
      }
      return null;
    });
  }

  public ResponseEntity<byte[]> getContent(final UUID id) {
    return imageStoreContentService.getById(id)
        .map(image ->
            ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(fileUtil.getSmallContent(image)))
        .orElse(ResponseEntity.noContent().build());
  }
}
