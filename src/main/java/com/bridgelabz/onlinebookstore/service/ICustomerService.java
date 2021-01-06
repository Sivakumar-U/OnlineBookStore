package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.model.Customer;
import com.bridgelabz.onlinebookstore.dto.CustomerDto;

public interface ICustomerService {

	Customer getCustomerDetails(String token);

	String addCustomerDetails(String token, CustomerDto customerDetails);
}
