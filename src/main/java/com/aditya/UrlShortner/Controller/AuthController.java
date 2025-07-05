package com.aditya.UrlShortner.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.UrlShortner.Service.UserService;
import com.aditya.UrlShortner.dto.UserRegistrationRequest;
import com.aditya.UrlShortner.model.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public User register(@RequestBody UserRegistrationRequest request) {
		return userService.registerUser(request);
	}
}
