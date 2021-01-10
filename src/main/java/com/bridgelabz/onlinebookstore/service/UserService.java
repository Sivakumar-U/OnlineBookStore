package com.bridgelabz.onlinebookstore.service;

import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

@Service
public class UserService implements IUserService {
	ForgotPasswordDto forgetPasswordDto;

	User userObj;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
			throw new UserException("No user found");
		}
		if (bCryptPasswordEncoder.matches(loginDto.getPassword(), userCheck.get().getPassword())) {
			String token = JwtGenerator.createJWT(userCheck.get().getUserId());
			userRepository.save(userCheck.get());
			return token;
		}
		throw new UserException("Incorrect credentials data");
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
			throw new UserException("User already verified");
		}
		return false;
	}

	@Override
	public Response forgetPassword(ForgotPasswordDto emailId) throws UserException {
		String url = "http://localhost:8080/swagger-ui.html#/user-controller/resetPasswordUsingPOST";
		Optional<User> isIdAvailable = userRepository.findByEmail(emailId.getEmailId());
		if (isIdAvailable == null) {
			throw new UserException("Email not present");
		}
		String token = JwtGenerator.createJWT(isIdAvailable.get().getUserId());
		final String username = "onlinebookstore2021@gmail.com";
		final String password = "Onlinebookstore@2021";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId.getEmailId()));
			message.setSubject("Reset your password");
			message.setText("Dear " + isIdAvailable.get().getFullName() + ","
					+ "\n\nWe're sending you this email because you requested a password reset. Click on this link to create a new password: "
					+ url
					+ "\n\n\nIf you didn't request a password reset, you can ignore this email. Your password will not be changed."
					+ "\n\n\nToken: " + token);

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return new Response(HttpStatus.OK.value(), "Email sending");
	}

	@Override
	public boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserException {
		//System.out.println(userObj.getEmailId());
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