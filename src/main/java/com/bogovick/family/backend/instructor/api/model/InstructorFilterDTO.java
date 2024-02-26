package com.bogovick.family.backend.instructor.api.model;

import lombok.Builder;

import java.util.List;

@Builder
public record InstructorFilterDTO(String username,
                                  Integer experience,
                                  String vehicleType,
                                  Integer vehicleYear,
                                  String transmissionType,
                                  List<String> selectedDistricts,
                                  String sex,
                                  Boolean privateInstructor,
                                  Boolean schoolInstructor,
                                  Boolean additionalBreak) {
}
