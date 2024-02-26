package com.bogovick.family.backend.instructor.repository.filter;

import com.bogovick.family.backend.instructor.api.model.InstructorFilterDTO;
import com.bogovick.family.backend.instructor.api.model.TransmissionType;
import com.bogovick.family.backend.instructor.core.entity.InstructorProfileEntity;
import com.bogovick.family.backend.instructor.core.entity.InstructorProfileEntity_;
import com.bogovick.family.backend.instructor.core.entity.SexType;
import com.bogovick.family.backend.location.core.entity.DistrictEntity_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface InstructorProfileSpecs {
  static Specification<InstructorProfileEntity> filterBy(InstructorFilterDTO filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Username filter
      if (filter.username() != null) {
        predicates.add(cb.like(cb.lower(root.get(InstructorProfileEntity_.username)), "%" + filter.username().toLowerCase() + "%"));
      }

      // Experience filter
      if (filter.experience() != null) {
        predicates.add(cb.equal(root.get(InstructorProfileEntity_.experience), filter.experience()));
      }

      // VehicleType filter
      if (filter.vehicleType() != null) {
        predicates.add(cb.equal(root.get(InstructorProfileEntity_.vehicleType), filter.vehicleType()));
      }

      // VehicleYear filter
      if (filter.vehicleYear() != null) {
        predicates.add(cb.equal(root.get(InstructorProfileEntity_.vehicleYear), filter.vehicleYear()));
      }

      // TransmissionType filter
      if (filter.transmissionType() != null) {
        predicates.add(cb.equal(root.get(InstructorProfileEntity_.vehicleTransmissionType), TransmissionType.valueOf(filter.transmissionType())));
      }

      // SelectedDistricts filter - assuming DistrictEntity has a name or some identifier field
      if (filter.selectedDistricts() != null && !filter.selectedDistricts().isEmpty()) {
        predicates.add(root.join(InstructorProfileEntity_.selectedDistricts).get(DistrictEntity_.code).in(filter.selectedDistricts()));
      }

      // Sex filter
      if (filter.sex() != null) {
        predicates.add(cb.equal(root.get(InstructorProfileEntity_.sex), SexType.valueOf(filter.sex())));
      }

      // PrivateInstructor filter
      if (filter.privateInstructor() != null) {
        predicates.add(cb.equal(root.get(InstructorProfileEntity_.privateInstructor), filter.privateInstructor()));
      }

      // SchoolInstructor filter
      if (filter.schoolInstructor() != null) {
        predicates.add(cb.equal(root.get(InstructorProfileEntity_.schoolInstructor), filter.schoolInstructor()));
      }

      // AdditionalBreak filter
      if (filter.additionalBreak() != null) {
        predicates.add(cb.equal(root.get(InstructorProfileEntity_.additionalBreak), filter.additionalBreak()));
      }

      return cb.and(predicates.toArray(Predicate[]::new));
    };
  }
}
