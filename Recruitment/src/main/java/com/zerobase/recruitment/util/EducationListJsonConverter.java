package com.zerobase.recruitment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.recruitment.entity.Education;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Convert
@RequiredArgsConstructor
public class EducationListJsonConverter implements AttributeConverter<List<Education>, String> {
  private final ObjectMapper objectMapper;

  @Override
  public String convertToDatabaseColumn(List<Education> attribute) {
    if (Objects.isNull(attribute)){
      return Strings.EMPTY;
    }
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("error converting list to json", e);
    }
  }

  @Override
  public List<Education> convertToEntityAttribute(String dbData) {
    if (Strings.isBlank(dbData)){
      return Collections.emptyList();
    }
    try {
      return objectMapper.readValue(dbData, new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      throw new RuntimeException("error converting json to list", e);
    }
  }
}
