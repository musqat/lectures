package com.zerobase.recruitment.dto;

import com.zerobase.recruitment.entity.Education;
import com.zerobase.recruitment.entity.Resume;
import java.util.List;

public class ResumeDto {

  public record Request(
      String title,
      List<EducationDto> educationList,
      String loginId
  ) {

    public Resume toEntity() {
      return Resume.builder()
          .title(title)
          .educationList(educationList.stream()
              .map(e -> Education.builder().school(e.school).degree(e.degree).build()).toList())
          .build();
    }
  }

//  @Builder
//  @Getter
//  public static class Response {
//    private Long recruitmentId;
//    private String title;
//    private Integer recruiterCount;
//    private LocalDateTime closingDate;
//    private RecruitmentStatus status;
//    private LocalDateTime modifyDate;
//    private LocalDateTime postingDate;
//    private Long companyMemberId;
//    private String companyName;
//
//
//  }

  public record EducationDto(
      String school,
      String degree
  ) {

  }
}
