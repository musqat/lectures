package com.muscat.accounts.mapper;

import com.muscat.accounts.dto.CustomerDetailsDto;
import com.muscat.accounts.dto.CustomerDto;
import com.muscat.accounts.entity.Customer;

public class CustomerMapper {


  public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
    customerDto.setName(customer.getName());
    customerDto.setEmail(customer.getEmail());
    customerDto.setMobileNumber(customer.getMobileNumber());
    return customerDto;
  }

  public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {
    customerDetailsDto.setName(customer.getName());
    customerDetailsDto.setEmail(customer.getEmail());
    customerDetailsDto.setMobileNumber(customer.getMobileNumber());
    return customerDetailsDto;
  }


  public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
    customer.setName(customerDto.getName());
    customer.setEmail(customerDto.getEmail());
    customer.setMobileNumber(customerDto.getMobileNumber());
    return customer;
  }


}
