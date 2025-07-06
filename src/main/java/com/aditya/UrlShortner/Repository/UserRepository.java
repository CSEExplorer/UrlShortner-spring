// UserRepository.java
package com.aditya.UrlShortner.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aditya.UrlShortner.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);

	User findByUsername(String username);
}
