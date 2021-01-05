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
	@Column
	private long userId;
	@Column
	private String fullName;
	@Column
	private String phoneNumber;
	@Column
	private long pinCode;
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

	public Customer() {
	}

	public Customer(CustomerDto customerDto) {
		this.fullName = customerDto.getFullName();
		this.phoneNumber = customerDto.getPhoneNumber();
		this.pinCode = customerDto.getPinCode();
		this.locality = customerDto.getLocality();
		this.address = customerDto.getAddress();
		this.state = customerDto.getState();
		this.state = customerDto.getState();
		this.landMark = customerDto.getLandMark();
		this.locationType = customerDto.getLocationType();
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return this.fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getLandMark() {
		return landMark;
	}

	public long getPinCode() {
		return this.pinCode;
	}
}