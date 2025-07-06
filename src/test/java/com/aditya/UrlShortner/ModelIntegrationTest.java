package com.aditya.UrlShortner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aditya.UrlShortner.Repository.UrlMappingRepository;
import com.aditya.UrlShortner.Repository.UserRepository;
import com.aditya.UrlShortner.model.UrlMapping;
import com.aditya.UrlShortner.model.User;

@SpringBootTest
public class ModelIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UrlMappingRepository urlMappingRepository;

	@Test
	public void testUserAndUrlMappingCreation() {
		// 1. Create and save a user
		User user = new User(null, "testuser", "testuser@example.com", "hashedpassword");
		User savedUser = userRepository.save(user);
		assertNotNull(savedUser.getId());

		// 2. Create and save a shortened URL
		UrlMapping url = UrlMapping.builder().originalUrl("https://example.com").shortCode("abc123")
				.createdAt(LocalDateTime.now()).clickCount(0).createdBy(savedUser).build();

		UrlMapping savedUrl = urlMappingRepository.save(url);
		assertNotNull(savedUrl.getId());
		assertEquals("abc123", savedUrl.getShortCode());

		// 3. Fetch and verify relationship
		UrlMapping fetched = urlMappingRepository.findByShortCode("abc123").orElse(null);
		assertNotNull(fetched);
		assertEquals(savedUser.getId(), fetched.getCreatedBy().getId());
	}
}
