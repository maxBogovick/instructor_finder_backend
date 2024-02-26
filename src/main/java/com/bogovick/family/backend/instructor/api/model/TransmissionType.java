package com.bogovick.family.backend.instructor.api.model;

import java.util.Map;

public enum TransmissionType {
  MANUAL, AUTOMAT;
  public static final Map<String, TransmissionType> map = EnumCacheUtil.buildEnumCache(values());
}
