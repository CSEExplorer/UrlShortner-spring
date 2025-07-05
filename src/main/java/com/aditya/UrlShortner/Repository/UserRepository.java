// UserRepository.java
package com.aditya.UrlShortner.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aditya.UrlShortner.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	User findByEmail(String email);

	User findByUsername(String username);
}
