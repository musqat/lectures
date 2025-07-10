package com.muscat.accounts.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "accounts")
public class AccountsContactInfoDto {

  private String message;
  private Map<String, String> contactDetails;
  private List<String> onCallSupport;
}
