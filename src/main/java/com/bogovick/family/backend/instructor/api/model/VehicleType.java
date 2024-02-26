package com.bogovick.family.backend.instructor.api.model;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public enum VehicleType {
  MOTOCICLE, CAR, TRACK, BUS, CAR_WITH_TRAILER;
  public static final Map<String, VehicleType> map = EnumCacheUtil.buildEnumCache(values());


}
