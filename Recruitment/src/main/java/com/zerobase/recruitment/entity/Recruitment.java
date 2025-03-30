package com.zerobase.recruitment.entity;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_member_id")
  private CompanyMember companyMember;

}
