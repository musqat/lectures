package com.zerobase.fastlms.member.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Member implements MemberCode{

  @Id
  private String userId;

  private String username;
  private String phone;
  private String password;
  private LocalDateTime regDt;
  private LocalDateTime udtDt; // 수정날짜

  private boolean emailAuthYn;
  private LocalDateTime emailAuthDt;
  private String emailAuthKey;

  private String resetPasswordKey;
  private LocalDateTime resetPasswordLimitDt;

  private boolean adminYn;

  private String userStatus; //이용가능한상태, 정지상태

  private String zipcode;
  private String addr;
  private String addrDetail;
}
