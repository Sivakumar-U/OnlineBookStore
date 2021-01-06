package com.bridgelabz.onlinebookstore.model;

import javax.persistence.*;
import com.bridgelabz.onlinebookstore.dto.CustomerDto;

import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sequenceNo;

	private long userId;

	private String fullName;
	@Column
	private String phoneNumber;
	@Column
	private String locality;
	@Column
	private String address;
	@Column
	private String city;
	@Column
	private String state;
	@Column
	private String landMark;
	@Column
	private String locationType;
	@Column
	private long pinCode;

	public Customer() {
	}

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