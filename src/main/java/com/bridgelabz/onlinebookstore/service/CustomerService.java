package com.bridgelabz.onlinebookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.CustomerDto;
import com.bridgelabz.onlinebookstore.model.Customer;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.CustomerRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.utility.JwtGenerator;

import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public CustomerRepository customerRepository;

	@Override
	public Customer getCustomerDetails(String token) {
		Long userId = JwtGenerator.decodeJWT(token);
		Optional<Customer> customer = customerRepository.findById(userId);
		return customer.get();
	}

	@Override
	public String addCustomerDetails(String token, CustomerDto customerDto) {
		Long userId = JwtGenerator.decodeJWT(token);
		Optional<User> user = userRepository.findById(userId);
		Optional<Customer> isCustomerAvailable = customerRepository.findByUserId(user.get().getUserId());
		if (isCustomerAvailable.isPresent()) {
			return "Your details are already saved";
		}
		Customer customer = new Customer(customerDto);
		customer.setUserId(userId);
		customerRepository.save(customer);
		return "Customer details added successfully";
	}
}