package com.zerobase.fastlms.course.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class TakeCourse implements TakeCourseCode {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  long courseId;
  String userId;

  long payPrice; //결제금액
  String status; //상태(수강신청, 결재완료, 수강취소)


  LocalDateTime regDt; //신청일
}
