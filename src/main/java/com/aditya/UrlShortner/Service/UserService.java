package com.aditya.UrlShortner.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aditya.UrlShortner.Repository.UserRepository;
import com.aditya.UrlShortner.dto.UserRegistrationRequest;
import com.aditya.UrlShortner.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public User registerUser(UserRegistrationRequest request) {
		// 1. Check if user/email exists
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email already in use");
		}

		if (userRepository.findByUsername(request.getUsername()) != null) {
			throw new RuntimeException("Username already in use");
		}

		// 2. Create and save new user
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword())); // hashed

		return userRepository.save(user);
	}
}
