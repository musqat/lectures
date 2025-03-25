package com.zerobase.fastlms.member.controller;


import com.zerobase.fastlms.common.model.ResponseResult;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;
import com.zerobase.fastlms.course.service.TakeCourseService;
import com.zerobase.fastlms.member.service.MemberService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApiMemberController {

  private final MemberService memberService;
  private final TakeCourseService takeCourseService;

  @PostMapping("/api/member/course/cancel.api")
  public ResponseEntity<?> memberTakeCourse(Model model,
      @RequestBody TakeCourseInput parameter,
      Principal principal) {

    String userId = principal.getName();
    TakeCourseDto detail = takeCourseService.detail(parameter.getTakeCourseId());
    // 내 수강 강좌인지 확인
    if (detail == null) {
      ResponseResult responseResult = new ResponseResult(false, "수강 신청 정보가 존재하지 않습니다.");
      return ResponseEntity.ok().body(responseResult);
    }
    if (userId == null || !userId.equals(detail.getUserId())) {
      //내 수강신청정보가 아님
      ResponseResult responseResult = new ResponseResult(false, "본인의 수강 신청 정보만 취소할수 있습니다.");
      return ResponseEntity.ok().body(responseResult);
    }

    ServiceResult result = takeCourseService.cancel(parameter.getTakeCourseId());
    if (!result.isResult()) {
      ResponseResult responseResult = new ResponseResult(false, result.getMessage());
      return ResponseEntity.ok().body(responseResult);
    }
    ResponseResult responseResult = new ResponseResult(true);
    return ResponseEntity.ok().body(responseResult);
  }

}
