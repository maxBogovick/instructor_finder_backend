package com.bogovick.family.backend.image.service;

import com.bogovick.family.backend.image.core.entity.ImageContentEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

@Component
public class FileUtil {
  @Value("${file.storage.base-dir}")
  private String baseDir; // Injected from application.yml

  public byte[] getSmallContent(final ImageContentEntity image) {
    try {
      return Files.readAllBytes(getFullPath(image));
    } catch(NoSuchFileException e) {
      return null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Path getFullPath(ImageContentEntity image) {
    return Path
        .of(baseDir)
        .resolve(image.getFilePath())
        .resolve(image.getName());
  }

  public String getBaseDir() {
    return baseDir;
  }

  public Path getRealPathForSave(Path path) {
    final Path resolve = Path.of(baseDir).resolve(path);
    final File file = resolve.toFile();
    if (file.exists()) {
      return resolve;
    }
    file.mkdirs();
    return resolve;
  }
}
