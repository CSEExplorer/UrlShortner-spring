package com.aditya.UrlShortner.Service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public boolean isAllowed(String key, int maxRequestsPerMinute) {
		String redisKey = "rate_limit:" + key;

		Long currentCount = redisTemplate.opsForValue().increment(redisKey);

		if (currentCount == 1) {
			// First hit — set expiry for 1 minute
			redisTemplate.expire(redisKey, 60, TimeUnit.SECONDS);
		}

		return currentCount <= maxRequestsPerMinute;
	}
}
