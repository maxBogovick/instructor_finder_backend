package com.bogovick.family.backend.instructor.facade;

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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorProfileFacade {
  private final InstructorProfileService instructorProfileService;
  private final InstructorProfileMapper mapper;
  private final DistrictService districtService;

  public Optional<InstructorProfileDto> saveInstructorProfile(InstructorProfileDto profile) {

    final InstructorProfileEntity entity = mapper.toEntity(profile);
    fillDistricts(profile, entity);
    fillVehicleTypes(profile, entity);
    fillTransmissionTypes(profile, entity);
    return instructorProfileService.saveInstructorProfile(entity).map(mapper::toDto);
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
    return mapper.toDtos(list);
  }

  public InstructorProfileDto getById(Long id) {
    if (id == null || id <= 0) {
      return null;
    }
    return instructorProfileService.getById(id).map(mapper::toDto).orElse(null);
  }
}
