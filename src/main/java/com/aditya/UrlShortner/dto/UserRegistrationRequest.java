package com.aditya.UrlShortner.dto;

import lombok.Data;

@Data
public class UserRegistrationRequest {
	private String username;
	private String email;
	private String password;
}
