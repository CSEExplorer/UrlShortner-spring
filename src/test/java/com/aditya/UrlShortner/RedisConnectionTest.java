package com.aditya.UrlShortner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionTest implements CommandLineRunner {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public void run(String... args) throws Exception {
		// Test writing to Redis
		redisTemplate.opsForValue().set("test-key", "Redis is working!");

		// Test reading from Redis
		String value = redisTemplate.opsForValue().get("test-key");
		System.out.println("🔁 Redis returned: " + value);

		if ("Redis is working!".equals(value)) {
			System.out.println("✅ Redis test passed!");
		} else {
			System.err.println("❌ Redis test failed.");
		}
	}
}
