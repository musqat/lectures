package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.common.model.ResponseResult;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;
import com.zerobase.fastlms.course.service.CourseService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class ApiCourseController extends baseController {

  private final CourseService courseService;
  private final CategoryService categoryService;

  @GetMapping("api/course/req.api")
  public ResponseEntity<?> courseReq(Model model
      , @RequestBody TakeCourseInput parameter
      , Principal principal) {

    parameter.setUserId(principal.getName());

    ServiceResult result = courseService.req(parameter);
    if(result.isResult()){

      ResponseResult responseResult = new ResponseResult(false, result.getMessage());
      return ResponseEntity.ok().body(responseResult);
    }
    ResponseResult responseResult = new ResponseResult(true);
    return ResponseEntity.ok().body(responseResult);
  }


}
