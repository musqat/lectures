package com.zerobase.recruitment.dto;

import com.zerobase.recruitment.entity.Education;
import com.zerobase.recruitment.enums.ApplicationStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class ApplicationDto {

  public record Request(
      Long memberId,
      Long resumeId
  ) {

  }

  @Builder
  @Getter
  public static class Response {
    private Long applicationId;
    private ApplicationStatus status;
    private LocalDateTime appliedDate;
    private Long resumeId;
    private String resumeTitle;
    private List<Education> educationList;
    private String name;

  }
}
