package com.bogovick.family.backend.image.service;

import com.bogovick.family.backend.image.api.model.ImageContentType;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

public final class FilePathGenerator {
  private FilePathGenerator() {
  }

  public static Path generateFilePath(ImageContentType imageContentType,
                               long profileId) {
    return Path.of(String.valueOf(profileId), imageContentType.getImageStorage());
  }

  public static String getUniqueFileName(final MultipartFile file) {
    String fileExtension = file.getOriginalFilename() != null
        ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
        : file.getName().substring(file.getOriginalFilename().lastIndexOf("."));
    return UUID.randomUUID() + fileExtension;
  }
}
