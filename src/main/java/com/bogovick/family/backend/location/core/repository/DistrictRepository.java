package com.bogovick.family.backend.location.core.repository;

import com.bogovick.family.backend.location.core.entity.DistrictEntity;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.List;

public interface DistrictRepository extends Repository<DistrictEntity, Long> {
  List<DistrictEntity> getByCodeIn(Collection<String> districts);
}
