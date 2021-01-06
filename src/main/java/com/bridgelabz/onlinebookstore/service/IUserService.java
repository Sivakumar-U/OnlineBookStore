package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.ForgotPasswordDto;
import com.bridgelabz.onlinebookstore.dto.LoginDto;
import com.bridgelabz.onlinebookstore.dto.RegistrationDto;
import com.bridgelabz.onlinebookstore.dto.ResetPasswordDto;
import com.bridgelabz.onlinebookstore.exception.UserException;

import com.bridgelabz.onlinebookstore.response.Response;

public interface IUserService {

	boolean register(RegistrationDto registrationDto) throws UserException;

	String login(LoginDto loginDto) throws UserException;

	boolean verify(String token) throws UserException;

	Response forgetPassword(ForgotPasswordDto emailId) throws UserException;

	boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserException;

}
