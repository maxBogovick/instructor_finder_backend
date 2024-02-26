package com.bogovick.family.backend.instructor.api.model;

import lombok.Builder;

import java.util.List;

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
    String mainProfileImageUrl,
    List<String> vehicleImageUrls,
    List<String> legalDocumentUrls,
    List<String> driverIdUrls,
    List<PhonesDto> phones,
    String createdUserInfo
) {
}
