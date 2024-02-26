package com.bogovick.family.backend.instructor.facade.mapper;

import com.bogovick.family.backend.instructor.api.model.InstructorProfileDto;
import com.bogovick.family.backend.instructor.api.model.PhonesDto;
import com.bogovick.family.backend.instructor.api.model.TransmissionType;
import com.bogovick.family.backend.instructor.api.model.VehicleType;
import com.bogovick.family.backend.instructor.core.entity.InstructorProfileEntity;
import com.bogovick.family.backend.location.core.entity.DistrictEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InstructorProfileMapper {

  @Mapping(target = "selectedDistricts", expression = "java(getSelectedDistincts(entity))")
  @Mapping(target = "instructorTransmissionTypes", expression = "java(getInstructorTransmissionTypes(entity))")
  @Mapping(target = "instructorVehicleTypes", expression = "java(getInstructorVehicleTypes(entity))")
  @Mapping(target = "phones", expression = "java(getPhones(entity))")
  InstructorProfileDto toDto(InstructorProfileEntity entity);

  default List<String> getPhones(InstructorProfileDto dto) {
    return CollectionUtils.isEmpty(dto.phones())
        ? Collections.emptyList()
        : dto.phones().stream().map(PhonesDto::value).toList();
  }

  default List<PhonesDto> getPhones(InstructorProfileEntity entity) {
    return CollectionUtils.isEmpty(entity.getPhones())
        ? Collections.emptyList()
        : entity.getPhones().stream().map(PhonesDto::new).toList();
  }

  default List<String> getSelectedDistincts(InstructorProfileEntity entity) {
    return CollectionUtils.isEmpty(entity.getSelectedDistricts())
        ? Collections.emptyList()
        : entity.getSelectedDistricts().stream().map(DistrictEntity::getCode).toList();
  }

  default List<String> getInstructorTransmissionTypes(InstructorProfileEntity entity) {
    return CollectionUtils.isEmpty(entity.getTransmissionTypes())
        ? Collections.emptyList()
        : entity.getTransmissionTypes().stream().map(TransmissionType::name).toList();
  }

  default List<String> getInstructorVehicleTypes(InstructorProfileEntity entity) {
    return CollectionUtils.isEmpty(entity.getVehicleTypes())
        ? Collections.emptyList()
        : entity.getVehicleTypes().stream().map(VehicleType::name).toList();
  }

  @Mapping(target = "sex", expression = "java(com.bogovick.family.backend.instructor.core.entity.SexType.valueOf(dto.sex()))")
  @Mapping(target = "vehicleTransmissionType", expression = "java(com.bogovick.family.backend.instructor.api.model.TransmissionType.valueOf(dto.transmissionType()))")
  @Mapping(target = "selectedDistricts", ignore = true)
  @Mapping(target = "transmissionTypes", ignore = true)
  @Mapping(target = "vehicleTypes", ignore = true)
  @Mapping(target = "phones", expression = "java(getPhones(dto))")
  InstructorProfileEntity toEntity(InstructorProfileDto dto);

  Collection<InstructorProfileDto> toDtos(List<InstructorProfileEntity> list);
}
