package com.aditya.UrlShortner.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.UrlShortner.Repository.UserRepository;
import com.aditya.UrlShortner.Service.UserService;
import com.aditya.UrlShortner.dto.LoginRequest;
import com.aditya.UrlShortner.dto.UserRegistrationRequest;
import com.aditya.UrlShortner.model.User;
import com.aditya.UrlShortner.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public User register(@RequestBody UserRegistrationRequest request) {
		return userService.registerUser(request);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail());
		if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return ResponseEntity.status(401).body("Invalid credentials");
		}

		String token = jwtUtil.generateToken(user.getEmail());
		return ResponseEntity.ok().body("Bearer " + token);
	}
}
