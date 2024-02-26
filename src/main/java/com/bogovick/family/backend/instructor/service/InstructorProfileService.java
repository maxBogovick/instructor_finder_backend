package com.bogovick.family.backend.instructor.service;

import com.bogovick.family.backend.instructor.api.model.InstructorFilterDTO;
import com.bogovick.family.backend.instructor.core.entity.InstructorProfileEntity;
import com.bogovick.family.backend.instructor.repository.InstructorProfileRepository;
import com.bogovick.family.backend.instructor.repository.filter.InstructorProfileSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorProfileService {
  private final InstructorProfileRepository instructorProfileRepository;

  @Transactional
  public Optional<InstructorProfileEntity> saveInstructorProfile(InstructorProfileEntity profile) {
    return Optional.ofNullable(instructorProfileRepository.save(profile));
  }

  @Transactional
  public List<InstructorProfileEntity> search(InstructorFilterDTO filter) {
    return instructorProfileRepository.findAll(InstructorProfileSpecs.filterBy(filter));
  }

  @Transactional(readOnly = true)
  public Optional<InstructorProfileEntity> getById(Long id) {
    if (id == null) {
      return null;
    }
    return instructorProfileRepository.findById(id);
  }
}
