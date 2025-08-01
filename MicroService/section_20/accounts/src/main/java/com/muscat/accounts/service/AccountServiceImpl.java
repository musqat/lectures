package com.muscat.accounts.service;


import com.muscat.accounts.constants.AccountConstants;
import com.muscat.accounts.dto.AccountsDto;
import com.muscat.accounts.dto.AccountsMsgDto;
import com.muscat.accounts.dto.CustomerDto;
import com.muscat.accounts.entity.Accounts;
import com.muscat.accounts.entity.Customer;
import com.muscat.accounts.exception.CustomerAlreadyExistsException;
import com.muscat.accounts.exception.ResourceNotFoundException;
import com.muscat.accounts.mapper.AccountsMapper;
import com.muscat.accounts.mapper.CustomerMapper;
import com.muscat.accounts.repository.AccountsRepository;
import com.muscat.accounts.repository.CustomerRepository;
import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

  private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

  private AccountsRepository accountsRepository;
  private CustomerRepository customerRepository;
  private final StreamBridge streamBridge;


  @Override
  public void createAccount(CustomerDto customerDto) {
    Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
    Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(
        customerDto.getMobileNumber());
    if (optionalCustomer.isPresent()) {
      throw new CustomerAlreadyExistsException(
          "Customer already registered with mobileNumber" + customerDto.getMobileNumber());
    }

    Customer savedCustomer = customerRepository.save(customer);
    Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
    sendCommunication(savedAccount, savedCustomer);
  }

  private void sendCommunication(Accounts account, Customer customer) {
    var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(),
        customer.getEmail(), customer.getMobileNumber());
    log.info("Sending Communication request for the details: {}", accountsMsgDto);
    var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
    log.info("Is the Communication request successfully triggered ? : {}", result);
  }

  /**
   * @param customer - Customer Object
   * @return the new account details
   */
  private Accounts createNewAccount(Customer customer) {
    Accounts newAccount = new Accounts();
    newAccount.setCustomerId(customer.getCustomerId());
    long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

    newAccount.setAccountNumber(randomAccNumber);
    newAccount.setAccountType(AccountConstants.SAVINGS);
    newAccount.setBranchAddress(AccountConstants.ADDRESS);

    return newAccount;
  }


  @Override
  public CustomerDto fetchAccount(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );
    Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
        () -> new ResourceNotFoundException("Account", "customerId",
            customer.getCustomerId().toString())
    );
    CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
    customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

    return customerDto;
  }

  @Override
  public boolean updateAccount(CustomerDto customerDto) {
    boolean isUpdated = false;
    AccountsDto accountsDto = customerDto.getAccountsDto();
    if (accountsDto != null) {
      Accounts accounts = accountsRepository.findByCustomerId(accountsDto.getAccountNumber())
          .orElseThrow(
              () -> new ResourceNotFoundException("Account", "AccountNumber",
                  accountsDto.getAccountNumber().toString()
              ));

      AccountsMapper.mapToAccounts(accountsDto, accounts);
      accounts = accountsRepository.save(accounts);

      Long customerId = accounts.getCustomerId();
      Customer customer = customerRepository.findById(customerId).orElseThrow(
          () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
      );

      CustomerMapper.mapToCustomer(customerDto, customer);
      customerRepository.save(customer);
      isUpdated = true;
    }

    return isUpdated;
  }

  @Override
  public boolean deleteAccount(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );
    accountsRepository.deleteByCustomerId(customer.getCustomerId());
    customerRepository.deleteById(customer.getCustomerId());
    return true;

  }

  @Override
  public boolean updateCommunicationStatus(Long accountNumber) {
    boolean isUpdated = false;
    if(accountNumber !=null ){
      Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
          () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
      );
      accounts.setCommunicationSw(true);
      accountsRepository.save(accounts);
      isUpdated = true;
    }
    return  isUpdated;
  }

}
