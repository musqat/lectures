package com.zerobase.fastlms.main.controller;

import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

  private final MailComponents mailComponents;

  @RequestMapping("/")
  public String index() {
//    String email = "hjs90561@naver.com";
//    String subject = "안녕하세요";
//    String text = "<p>반갑습니다</p><p>안녕하세요</p>";
//    mailComponents.sendMail(email, subject, text);
    return "index";
  }


  @RequestMapping("/error/denied")
  public String errorDenied(){
    return "error/denied";
  }


}
