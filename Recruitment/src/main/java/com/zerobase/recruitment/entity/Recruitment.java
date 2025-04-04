package com.zerobase.recruitment.entity;

import com.zerobase.recruitment.dto.RecruitmentDto;
import com.zerobase.recruitment.enums.RecruitmentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recruitment_id")
  private Long id;

  private String title;
  private Integer recruiterCount;
  private LocalDateTime closingDate;
  @Enumerated(EnumType.STRING)
  private RecruitmentStatus status;
  @UpdateTimestamp
  private LocalDateTime modifyDate;
  @CreationTimestamp
  private LocalDateTime postingDate;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitment")
  private List<Application> applicationList;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_member_id")
  private CompanyMember companyMember;

  @Builder
  public Recruitment(
      String title,
      Integer recruiterCount,
      LocalDateTime closingDate
  ) {
    this.title = title;
    this.recruiterCount = recruiterCount;
    this.closingDate = closingDate;
  }

  public void opening() {
    this.status = RecruitmentStatus.OPEN;
  }
  public void closing() {
    this.status = RecruitmentStatus.CLOSE;
  }


  public RecruitmentDto.Response toDto() {
    return RecruitmentDto.Response.builder()
        .recruitmentId(this.id)
        .title(this.title)
        .recruiterCount(this.recruiterCount)
        .closingDate(this.closingDate)
        .status(this.status)
        .modifyDate(this.modifyDate)
        .postingDate(this.postingDate)
        .companyMemberId(this.companyMember.getId())
        .companyName(this.companyMember.getCompanyName())
        .build();
  }

  public Recruitment update(RecruitmentDto.Request request) {
    this.title = request.title();
    this.recruiterCount = request.recruiterCount();
    this.closingDate = request.closingDate();
    this.status = request.status();
    return this;
  }
}
