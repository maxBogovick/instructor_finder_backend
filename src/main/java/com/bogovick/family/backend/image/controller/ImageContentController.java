package com.bogovick.family.backend.image.controller;

import com.bogovick.family.backend.image.api.model.ImageContentDto;
import com.bogovick.family.backend.image.api.model.ImageContentType;
import com.bogovick.family.backend.image.facade.ImageStorageFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("api/image")
@RequiredArgsConstructor
@Slf4j
public class ImageContentController {
  private final ImageStorageFacade imageStorageFacade;


  @PostMapping("/upload/{profileId}/{imageContentType}/{order}")
  public ResponseEntity<ImageContentDto> uploadImage(
      @PathVariable(name = "profileId") long profileId,
      @PathVariable(name = "order") int order,
      @PathVariable(name = "imageContentType") ImageContentType imageContentType,
      @RequestParam("file") MultipartFile file) {
    try {
      val savedImage = imageStorageFacade.saveContent(file, imageContentType, profileId, order);
      return new ResponseEntity<>(savedImage.get(1, TimeUnit.SECONDS), HttpStatus.OK);
    } catch (ExecutionException | InterruptedException | TimeoutException e) {
      log.error("error during save image for profileId {}, order {}, imageContentType {} and file {}",
          profileId, order, imageContentType, file != null ? file.getName() : "");
      throw new RuntimeException(e);
    }
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<byte[]> getById(@PathVariable(name = "id") final UUID id) {
    return imageStorageFacade.getContent(id);
  }
}
