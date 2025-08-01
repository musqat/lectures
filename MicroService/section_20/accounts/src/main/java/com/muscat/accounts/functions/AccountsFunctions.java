package com.muscat.accounts.functions;

import com.muscat.accounts.service.IAccountService;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountsFunctions {

  private static final Logger log = LoggerFactory.getLogger(AccountsFunctions.class);

  @Bean
  public Consumer<Long> updateCommunication(IAccountService accountService){
    return accountNumber -> {
      log.info("Updating Communication status for the account number : " + accountNumber.toString());
      accountService.updateCommunicationStatus(accountNumber);
    };
  }

}
