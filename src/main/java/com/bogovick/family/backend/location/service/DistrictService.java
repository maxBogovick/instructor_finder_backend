package com.bogovick.family.backend.location.service;

import com.bogovick.family.backend.location.core.entity.DistrictEntity;
import com.bogovick.family.backend.location.core.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {

  private final DistrictRepository districtRepository;


  public List<DistrictEntity> getAllDistrictsByCode(List<String> districts) {
    if (CollectionUtils.isEmpty(districts)) {
      return Collections.emptyList();
    }
    return districtRepository.getByCodeIn(districts);
  }
}
