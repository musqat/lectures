package com.zerobase.cms.user.client;


import com.zerobase.cms.user.client.mailgun.SendMailForm;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {

    @PostMapping("../messages")
    ResponseEntity<String> sendEmail(@SpringQueryMap SendMailForm form);

}
