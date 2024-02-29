package com.bogovick.family.backend.instructor.api.model;

import com.bogovick.family.backend.common.api.model.BackendErrorDto;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record InstructorProfileDto(
    Long id,
    String username,
    Integer experience,
    String email,
    String vehicleName,
    String vehicleType,
    Integer vehicleYear,
    String transmissionType,
    List<String> selectedDistricts,
    List<String> instructorTransmissionTypes,
    List<String> instructorVehicleTypes,
    String bio,
    String sex,
    Boolean privateInstructor,
    Boolean schoolInstructor,
    Boolean additionalBreak,
    UUID mainProfileImageUrl,
    List<UUID> vehicleImageUrls,
    List<UUID> legalDocumentUrls,
    List<UUID> driverIdUrls,
    List<PhonesDto> phones,
    String createdUserInfo,
    BackendErrorDto error,
    boolean validated) {
}
