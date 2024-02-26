package com.bogovick.family.backend.instructor.api.model;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class EnumCacheUtil {
  private EnumCacheUtil() {
  }

  public static <T extends Enum<T>> Map<String, T> buildEnumCache(T[] values) {
    return Arrays.stream(values).collect(Collectors.toMap(Enum::name, Function.identity()));
  }

  public static <T extends Enum<T>> List<T> getTypesByNames(List<String> keys, Map<String, T> map) {
    if (CollectionUtils.isEmpty(keys)) {
      return Collections.emptyList();
    }
    return keys.stream().map(map::get).filter(Objects::nonNull).distinct().toList();
  }
}
