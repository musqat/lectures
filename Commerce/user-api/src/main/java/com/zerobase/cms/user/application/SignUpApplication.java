package com.zerobase.cms.user.application;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.customer.SignUpCustomerService;
import com.zerobase.cms.user.service.seller.SellerService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

  private final MailgunClient mailgunClient;
  private final SignUpCustomerService signUpCustomerService;
  private final SellerService sellerService;

  public void customerVerity(String email, String code) {
    signUpCustomerService.verifyEmail(email, code);
  }

  public String customerSignUp(SignUpForm form) {
    if (signUpCustomerService.isEmailExist(form.getEmail())) {
      throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
    } else {
      Customer c = signUpCustomerService.signUp(form);
      LocalDateTime now = LocalDateTime.now();

      String code = getRandomCode();
      SendMailForm sendMailForm = SendMailForm.builder()
          .from("test@mytest.com")
          .to(form.getEmail())
          .subject("Verification Email!")
          .text(getVerificationEmailBody(c.getEmail(), c.getName(), "customer", code))
          .build();

//            mailgunClient.sendEmail(sendMailForm);
      signUpCustomerService.ChangeCustomerValidateEmail(c.getId(), code);
      return "회원 가입에 성공하였습니다.";
    }

  }

  public void sellerVerity(String email, String code) {
    sellerService.verifyEmail(email, code);
  }

  public String sellerSignUp(SignUpForm form) {
    if (sellerService.isEmailExist(form.getEmail())) {
      throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
    } else {
      Seller s = sellerService.signUp(form);
      LocalDateTime now = LocalDateTime.now();

      String code = getRandomCode();
      SendMailForm sendMailForm = SendMailForm.builder()
          .from("test@mytest.com")
          .to(form.getEmail())
          .subject("Verification Email!")
          .text(getVerificationEmailBody(s.getEmail(), s.getName(), "seller", code))
          .build();

//            mailgunClient.sendEmail(sendMailForm);
      sellerService.ChangeSellerValidateEmail(s.getId(), code);
      return "회원 가입에 성공하였습니다.";
    }


  }

  private String getRandomCode() {
    return RandomStringUtils.random(10, true, true);
  }

  private String getVerificationEmailBody(String email, String name, String type, String code) {
    StringBuilder builder = new StringBuilder();
    return builder.append("Hello ").append(name).append("! Please Click Link for verification.\n\n")
        .append("http://localhost:8080/signup/" + type + "verify/?email=")
        .append(email)
        .append("&code=")
        .append(code).toString();

  }
}
