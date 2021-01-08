package com.bridgelabz.onlinebookstore.model;

import javax.persistence.*;
import com.bridgelabz.onlinebookstore.dto.CustomerDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "customer")
@Data
//@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sequenceNo;

	private long userId;

	private String fullName;
	private String phoneNumber;
	private String locality;
	private String address;
	private String city;
	private String state;
	private String landMark;
	private String locationType;
	private long pinCode;

	public Customer(CustomerDto customerDto) {
		this.fullName = customerDto.getFullName();
		this.phoneNumber = customerDto.getPhoneNumber();
		this.locality = customerDto.getLocality();
		this.address = customerDto.getAddress();
		this.state = customerDto.getState();
		this.city = customerDto.getCity();
		this.landMark = customerDto.getLandMark();
		this.locationType = customerDto.getLocationType();
		this.pinCode = customerDto.getPinCode();
	}
}