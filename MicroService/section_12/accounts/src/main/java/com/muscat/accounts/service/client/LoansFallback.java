package com.muscat.accounts.service.client;

import com.muscat.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {

  @Override
  public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
    return null;
  }
}
