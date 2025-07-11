package com.aditya.UrlShortner.Service;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.IOException;
import jakarta.annotation.PostConstruct;

@Service
public class RateLimiterService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@PostConstruct
	public void debugRedisHost() {
		System.out.println(
				"🟢 Redis host = " + ((LettuceConnectionFactory) redisTemplate.getConnectionFactory()).getHostName());
	}

	@PostConstruct
	public void printProperties() throws IOException, java.io.IOException {
		Properties props = new Properties();
		props.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		System.out.println("🔍 Properties loaded in container:");
		props.forEach((k, v) -> System.out.println(k + " = " + v));
	}

	public boolean isAllowed(String key, int maxRequestsPerMinute) {
		String redisKey = "rate_limit:" + key;

		try {
			Long currentCount = redisTemplate.opsForValue().increment(redisKey);

			if (currentCount == 1) {
				redisTemplate.expire(redisKey, 60, TimeUnit.SECONDS);
			}

			System.out.println("✅ Redis increment successful: " + currentCount);
			return currentCount <= maxRequestsPerMinute;
		} catch (Exception e) {
			System.err.println("❌ Redis connection failed in RateLimiterService");
			e.printStackTrace();
			return true; // fail-open: allow request instead of blocking
		}
	}

}
