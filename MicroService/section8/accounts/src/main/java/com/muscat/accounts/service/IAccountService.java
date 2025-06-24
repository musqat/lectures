package com.muscat.accounts.service;


import com.muscat.accounts.dto.CustomerDto;

public interface IAccountService {

  /**
   * @param customerDto - customer Object
   */
  void createAccount(CustomerDto customerDto);


  /**
   * @param mobileNumber - Input Mobile Number
   * @return Accounts Details based on a given mobileNumber
   */
  CustomerDto fetchAccount(String mobileNumber);

  /**
   * @param customerDto - Customer Object
   * @return boolean indicating if the update of Account details is successful or not
   */
  boolean updateAccount(CustomerDto customerDto);


  /**
   * @param mobileNumber - Input Mobile Number
   * @return Accounts Details based on a given mobileNumber
   */
  boolean deleteAccount(String mobileNumber);


}
