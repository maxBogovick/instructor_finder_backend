package com.bogovick.family.backend.image.service;

import com.bogovick.family.backend.image.api.model.ImageContentType;
import com.bogovick.family.backend.image.core.entity.ImageContentEntity;
import com.bogovick.family.backend.image.core.repository.ImageContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ImageStoreContentService {

  private final ImageContentRepository imageContentRepository;

  @Transactional
  public ImageContentEntity save(ImageContentEntity imageContentEntity) {
    if (imageContentEntity == null) {
      return null;
    }
    return imageContentRepository.save(imageContentEntity);
  }

  @Transactional(readOnly = true)
  public Optional<ImageContentEntity> getById(UUID id) {
    return imageContentRepository.findById(id);
  }

  public UUID getBy(ImageContentType imageContentType, Long profileId) {
    return imageContentRepository.getBy(imageContentType, profileId).orElseGet(() -> {
      log.warn("search image url by {} and {} do nothing", imageContentType, profileId);
      return null;
    });
  }

  public List<UUID> getAllBy(ImageContentType imageContentType, Long profileId) {
    final List<UUID> allBy = imageContentRepository.getAllBy(imageContentType, profileId);
    if (CollectionUtils.isEmpty(allBy)) {
      log.warn("search image url by {} and {} do nothing", imageContentType, profileId);
    }
    return allBy;
  }
}
