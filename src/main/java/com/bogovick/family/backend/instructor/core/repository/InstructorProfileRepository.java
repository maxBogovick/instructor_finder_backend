package com.bogovick.family.backend.instructor.core.repository;

import com.bogovick.family.backend.instructor.core.entity.InstructorProfileEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface InstructorProfileRepository extends CrudRepository<InstructorProfileEntity, Long>, JpaSpecificationExecutor<InstructorProfileEntity> {
}
