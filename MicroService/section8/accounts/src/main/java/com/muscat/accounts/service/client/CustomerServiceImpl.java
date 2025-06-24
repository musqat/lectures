package com.muscat.accounts.service.client;

import com.muscat.accounts.dto.AccountsDto;
import com.muscat.accounts.dto.CardsDto;
import com.muscat.accounts.dto.CustomerDetailsDto;
import com.muscat.accounts.dto.LoansDto;
import com.muscat.accounts.entity.Accounts;
import com.muscat.accounts.entity.Customer;
import com.muscat.accounts.exception.ResourceNotFoundException;
import com.muscat.accounts.mapper.AccountsMapper;
import com.muscat.accounts.mapper.CustomerMapper;
import com.muscat.accounts.repository.AccountsRepository;
import com.muscat.accounts.repository.CustomerRepository;
import com.muscat.accounts.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

  private AccountsRepository accountsRepository;
  private CustomerRepository customerRepository;
  private CardsFeignClient cardsFeignClient;
  private LoansFeignClient loansFeignClient;

  /**
   * @param mobileNumber - Input Mobile Number
   * @return Customer Details based on a given mobileNumber
   */
  @Override
  public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );
    Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
        () -> new ResourceNotFoundException("Account", "customerId",
            customer.getCustomerId().toString())
    );

    CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,
        new CustomerDetailsDto());
    customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

    ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(
        mobileNumber);
    customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

    ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(
        mobileNumber);
    customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());


    return customerDetailsDto;
  }
}
