package com.aditya.UrlShortner;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class MongoConnectionTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	void testMongoConnection() {
		boolean exists = mongoTemplate.collectionExists("users");
		System.out.println("Connection test passed, collection exists: " + exists);
		assertTrue(true); // Ensures test passes if no exception
	}
}
