package com.bridgelabz.onlinebookstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.dto.ForgotPasswordDto;
import com.bridgelabz.onlinebookstore.dto.LoginDto;
import com.bridgelabz.onlinebookstore.dto.RegistrationDto;
import com.bridgelabz.onlinebookstore.dto.ResetPasswordDto;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.response.Response;
import com.bridgelabz.onlinebookstore.service.IUserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bookstore")
public class UserController {

	@Autowired
	public IUserService userService;

	@ApiOperation("For signup")
	@PostMapping("/signup")
	public ResponseEntity<Response> register(@RequestBody @Valid RegistrationDto registrationDto) throws UserException {
		if (userService.register(registrationDto))
			return new ResponseEntity<>(new Response(200, "user register successful"), HttpStatus.OK);
		else
			return new ResponseEntity<>(new Response(400, "user register un-successful"), HttpStatus.BAD_REQUEST);
	}

	@ApiOperation("For signin")
	@PostMapping("/signin")
	public ResponseEntity<Response> login(@RequestBody @Valid LoginDto loginDTO) throws UserException {
		String token = userService.login(loginDTO);
		if (token != null) {
			return new ResponseEntity<>(new Response(200, "User login successful", token), HttpStatus.OK);
		}
		return new ResponseEntity<>(new Response(400, "User login un-successful"), HttpStatus.NOT_ACCEPTABLE);
	}

	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> userVerification(@PathVariable("token") String token) throws UserException {
		if (userService.verify(token))
			return new ResponseEntity<>(new Response(200, "User verified successfully"), HttpStatus.OK);

		return new ResponseEntity<>(new Response(400, "User verification failed"), HttpStatus.NOT_ACCEPTABLE);
	}

	@PostMapping("/forget/password")
	public ResponseEntity<Response> forgotPassword(@Valid @RequestBody ForgotPasswordDto emailId) throws UserException {
		Response response = userService.forgetPassword(emailId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/reset/password")
	public ResponseEntity<Response> resetPassword(@Valid @RequestBody ResetPasswordDto resetPassword,
			@RequestHeader String token) throws UserException {
		if (userService.resetPassword(resetPassword, token))
			return new ResponseEntity<>(new Response(200, "User password reset successful"), HttpStatus.OK);

		return new ResponseEntity<>(new Response(400, "User password reset unsuccessful"), HttpStatus.NOT_ACCEPTABLE);
	}
}