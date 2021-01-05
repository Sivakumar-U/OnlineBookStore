package com.bridgelabz.onlinebookstore.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerDto {

	private String fullName;
	private String phoneNumber;
	private String locality;
	private String landMark;
	private String address;
	private String state;
	private String locationType;
	private long pinCode;

	public String getFullName() {
		return fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public long getPinCode() {
		return pinCode;
	}

	public String getLocality() {
		return locality;
	}

	public String getAddress() {
		return address;
	}

	public String getState() {
		return state;
	}

	public String getLandMark() {
		return landMark;
	}

	public String getLocationType() {
		return locationType;
	}
}