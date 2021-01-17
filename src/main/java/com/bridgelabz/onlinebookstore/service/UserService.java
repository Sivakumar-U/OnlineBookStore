package com.bridgelabz.onlinebookstore.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.ForgotPasswordDto;
import com.bridgelabz.onlinebookstore.dto.LoginDto;
import com.bridgelabz.onlinebookstore.dto.RegistrationDto;
import com.bridgelabz.onlinebookstore.dto.ResetPasswordDto;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.response.Response;
import com.bridgelabz.onlinebookstore.utility.JwtGenerator;
import com.bridgelabz.onlinebookstore.utility.MailData;

@Service
public class UserService implements IUserService {
	ForgotPasswordDto forgetPasswordDto;

	User userObj;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	MailData mailData;

	private static final String VERIFICATION_URL = "http://localhost:8080/swagger-ui.html#/user-controller/userVerificationUsingGET";

	public boolean register(RegistrationDto registrationDto) {
		Optional<User> isEmailAvailable = userRepository.findByEmail(registrationDto.getEmailId());
		if (isEmailAvailable.isPresent()) {
			return false;
		} else {
			User userDetails = new User();
			BeanUtils.copyProperties(registrationDto, userDetails);
			userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
			userObj = userRepository.save(userDetails);
			getResponse(userDetails.getUserId());
			return true;
		}

	}

	private String getResponse(long userId) {
		String response = "Thanking you for Registartion with us\n\n"
				+ "Click on the below link for the verification\n\n" + VERIFICATION_URL
				+ JwtGenerator.createJWT(userId);
		return response;
	}

	@Override
	public String login(LoginDto loginDto) throws UserException {
		Optional<User> userCheck = userRepository.findByEmail(loginDto.getEmailId());
		if (!userCheck.isPresent()) {
			return "No user found.";
		}
		if (bCryptPasswordEncoder.matches(loginDto.getPassword(), userCheck.get().getPassword())) {
			String token = JwtGenerator.createJWT(userCheck.get().getUserId());
			userRepository.save(userCheck.get());
			return token;
		}
		return "user password is not correct.";
	}

	@Override
	public Response forgetPassword(ForgotPasswordDto emailId) throws UserException {
		String url = "http://localhost:4200/reset-password/";
		Optional<User> isIdAvailable = userRepository.findByEmail(emailId.getEmailId());
		if (isIdAvailable.isEmpty()) {
			return new Response(400, "Email not present");
		}

		else {
			String token = JwtGenerator.createJWT(isIdAvailable.get().getUserId());
			mailData.sendMessage("Reset your password", emailId.getEmailId(), isIdAvailable.get().getFullName(),
					"\n\nWe're sending you this email because you requested a password reset. Click on this link to create a new password: ",
					url, token,
					"\n\n\nIf you didn't request a password reset, you can ignore this email. Your password will not be changed. \n\n");
			return new Response(HttpStatus.OK.value(), "Check your mail for reset password link");

		}
	}

	@Override
	public boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserException {
		if (resetPassword.getNewPassword().equals(resetPassword.getConfirmPassword())) {
			long id = JwtGenerator.decodeJWT(token);
			User isIdAvailable = userRepository.findById(id).get();
			if (isIdAvailable != null) {
				isIdAvailable.setPassword(bCryptPasswordEncoder.encode((resetPassword.getNewPassword())));
				userRepository.save(isIdAvailable);
				return true;
			}
			throw new UserException("User not exist");
		}
		return false;
	}

}