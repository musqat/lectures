package com.zerobase.recruitment.entity;

import com.zerobase.recruitment.enums.ResumeStatus;
import com.zerobase.recruitment.util.EducationListJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
public class Resume {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "resume_id")
  private Long id;
  private String title;
  @Convert(converter = EducationListJsonConverter.class)
  @Column(columnDefinition = "TEXT")
  private List<Education> educationList;
  @Enumerated(EnumType.STRING)
  private ResumeStatus status;
  @UpdateTimestamp
  private LocalDateTime modifyDate;
  @CreationTimestamp
  private LocalDateTime postingDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;


}
