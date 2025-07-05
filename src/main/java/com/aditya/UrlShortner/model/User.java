package com.aditya.UrlShortner.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
	@Id
	private String id;

	@Indexed(unique = true)
	private String username;

	@Indexed(unique = true)
	private String email;

	private String password;

}
