package com.bogovick.family.backend.instructor.facade;

import com.bogovick.family.backend.common.api.model.BackendErrorDto;
import com.bogovick.family.backend.image.api.model.ImageContentType;
import com.bogovick.family.backend.image.service.ImageStoreContentService;
import com.bogovick.family.backend.instructor.api.model.EnumCacheUtil;
import com.bogovick.family.backend.instructor.api.model.InstructorFilterDTO;
import com.bogovick.family.backend.instructor.api.model.InstructorProfileDto;
import com.bogovick.family.backend.instructor.api.model.TransmissionType;
import com.bogovick.family.backend.instructor.api.model.VehicleType;
import com.bogovick.family.backend.instructor.core.entity.InstructorProfileEntity;
import com.bogovick.family.backend.instructor.facade.mapper.InstructorProfileMapper;
import com.bogovick.family.backend.instructor.service.InstructorProfileService;
import com.bogovick.family.backend.location.core.entity.DistrictEntity;
import com.bogovick.family.backend.location.service.DistrictService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.bogovick.family.backend.common.api.model.BackendErrorDto.BackendErrorCode.UNKNOWN_EXCEPTION;
import static com.bogovick.family.backend.common.api.model.BackendErrorDto.BackendErrorCode.USERNAME_PASSWORD_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class InstructorProfileFacade {
  private final InstructorProfileService instructorProfileService;
  private final InstructorProfileMapper mapper;
  private final DistrictService districtService;
  private final ImageStoreContentService imageStoreContentService;

  private static final ExecutorService IMAGE_EXECUTION_SERVICE = Executors.newVirtualThreadPerTaskExecutor();

  public Optional<InstructorProfileDto> saveInstructorProfile(InstructorProfileDto profile) {

    final InstructorProfileEntity entity = mapper.toEntity(profile);
    fillDistricts(profile, entity);
    fillVehicleTypes(profile, entity);
    fillTransmissionTypes(profile, entity);

    try {
      return instructorProfileService.saveInstructorProfile(entity).map(item ->
          mapper.toDto(item, null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null));
    } catch (Exception e) {
      val error = BackendErrorDto.builder();
      if (e instanceof DataIntegrityViolationException) {
        error
            .errorCode(USERNAME_PASSWORD_ALREADY_EXISTS)
            .errorMessage(e.getMessage());
      } else {
        error
            .errorCode(UNKNOWN_EXCEPTION)
            .errorMessage(e.getMessage());
      }
      return Optional.of(mapper.toDto(null, null, Collections.emptyList(), Collections.emptyList(),
          Collections.emptyList(),
          error.build()));
    }
  }

  private CompletableFuture<UUID> getSingleImageAsync(ImageContentType imageContentType, Long id) {
    return CompletableFuture.supplyAsync(() -> imageStoreContentService.getBy(imageContentType, id), IMAGE_EXECUTION_SERVICE);
  }

  private CompletableFuture<List<UUID>> getAllImageAsync(ImageContentType imageContentType, Long id) {
    return CompletableFuture.supplyAsync(() -> imageStoreContentService.getAllBy(imageContentType, id), IMAGE_EXECUTION_SERVICE);
  }

  private void fillTransmissionTypes(InstructorProfileDto profile, InstructorProfileEntity entity) {
    entity.setTransmissionTypes(EnumCacheUtil.getTypesByNames(profile.instructorTransmissionTypes(), TransmissionType.map));
  }

  private void fillVehicleTypes(InstructorProfileDto profile, InstructorProfileEntity entity) {
    entity.setVehicleTypes(EnumCacheUtil.getTypesByNames(profile.instructorVehicleTypes(), VehicleType.map));
  }

  private void fillDistricts(InstructorProfileDto profile, InstructorProfileEntity entity) {
    final List<DistrictEntity> districts = districtService.getAllDistrictsByCode(profile.selectedDistricts());
    if (!districts.isEmpty()) {
      entity.setSelectedDistricts(districts);
    }
  }

  public Collection<InstructorProfileDto> search(InstructorFilterDTO filter) {
    List<InstructorProfileEntity> list = instructorProfileService.search(filter);
    if (CollectionUtils.isEmpty(list)) {
      return Collections.emptyList();
    }
    return list.stream().map(this::toDtoInternal).toList();
  }

  public InstructorProfileDto getById(Long id) {
    if (id == null || id <= 0) {
      return null;
    }
    return instructorProfileService.getById(id).map(this::toDtoInternal).orElse(null);
  }

  private InstructorProfileDto toDtoInternal(InstructorProfileEntity entity) {
    val avatarUrl = getSingleImageAsync(ImageContentType.AVATAR, entity.getId());
    val drivingCardUrls = getAllImageAsync(ImageContentType.DRIVER_CARD, entity.getId());
    val vehicleUrls = getAllImageAsync(ImageContentType.VEHICLE, entity.getId());
    val legalDocumentUrls = getAllImageAsync(ImageContentType.LEGAL_DOCUMENT, entity.getId());
    CompletableFuture.allOf(
        avatarUrl,
        drivingCardUrls,
        vehicleUrls,
        legalDocumentUrls
    ).join();
    return mapper.toDto(entity, avatarUrl.join(), drivingCardUrls.join(), vehicleUrls.join(), legalDocumentUrls.join(), null);
  }
}
