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
import com.bridgelabz.onlinebookstore.response.EmailObject;
import com.bridgelabz.onlinebookstore.response.Response;
import com.bridgelabz.onlinebookstore.utility.JwtGenerator;
//import com.bridgelabz.onlinebookstore.utility.RabbitMQSender;

@Service
public class UserService implements IUserService {
	@Autowired
	public UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

//	@Autowired
//	private RabbitMQSender rabbitMQSender;

	private static final String VERIFICATION_URL = "http://localhost:3000/verify/";
//	private static final String RESETPASSWORD_URL = "http://localhost:8080/bookstore/resetpassword?token=";

	public boolean register(RegistrationDto registrationDto) {
		Optional<User> isEmailAvailable = userRepository.findByEmail(registrationDto.getEmailId());
//		if (isEmailAvailable.isPresent()) {
//			return false;
//			//throw new UserException("email exist");
//		}
		User userDetails = new User();
		BeanUtils.copyProperties(registrationDto, userDetails);
		userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
//		return userRepository.save(userDetails);
//		String response = getResponse(userDetails.getUserId());
//		if (rabbitMQSender.send(new EmailObject(registrationDto.getEmailId(), "Registration Link...", response)))
//			return true;
	return true;
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
			throw new UserException("No user found", UserException.ExceptionType.INVALID_USER);
		}
		if (bCryptPasswordEncoder.matches(loginDto.getPassword(), userCheck.get().getPassword())) {
			String token = JwtGenerator.createJWT(userCheck.get().getUserId());
			userRepository.save(userCheck.get());
			return token;
		}
		throw new UserException("Incorrect credentials", UserException.ExceptionType.INVALID_CREDENTIALS);
	}

	@Override
	public boolean verify(String token) throws UserException {
		long id = JwtGenerator.decodeJWT(token);
		User userInfo = userRepository.findById(id).get();
		if (id > 0 && userInfo != null) {
			if (!userInfo.isVerify()) {
				userInfo.setVerify(true);
				userRepository.save(userInfo);
				return true;
			}
			throw new UserException("User already verified", UserException.ExceptionType.ALREADY_VERFIED);
		}
		return false;
	}

//	@Override
//	public Response forgetPassword(ForgotPasswordDto userMail) {
//		User isIdAvailable = userRepository.findByEmail(userMail.getEmailId()).get();
//		if (isIdAvailable != null && isIdAvailable.isVerify()) {
//			String token = JwtGenerator.createJWT(isIdAvailable.getUserId());
//			String response = RESETPASSWORD_URL + token;
//			if (rabbitMQSender.send(new EmailObject(isIdAvailable.getEmailId(), "ResetPassword Link...", response)))
//				return new Response(HttpStatus.OK.value(), "ResetPassword link Successfully", token);
//		}
//		return new Response(HttpStatus.OK.value(), "Email sending failed");
//	}

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
			throw new UserException("User not exist", UserException.ExceptionType.INVALID_USER);
		}
		return false;
	}

}