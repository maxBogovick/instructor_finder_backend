package com.bogovick.family.backend.util.core;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Converter
public class CollectionToStringConverter implements AttributeConverter<List<String>, String> {

  public static final String SEPARATOR = ",";

  @Override
  public String convertToDatabaseColumn(List<String> attribute) {
    return CollectionUtils.isEmpty(attribute) ? null : String.join(SEPARATOR, attribute);
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    return !StringUtils.hasText(dbData) ? Collections.emptyList() : Arrays.stream(dbData.split(SEPARATOR)).toList();
  }
}
