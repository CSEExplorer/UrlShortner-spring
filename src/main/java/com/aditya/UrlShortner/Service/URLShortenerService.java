package com.aditya.UrlShortner.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aditya.UrlShortner.Repository.UrlMappingRepository;
import com.aditya.UrlShortner.Repository.UserRepository;
import com.aditya.UrlShortner.model.UrlMapping;
import com.aditya.UrlShortner.model.User;

@Service
public class URLShortenerService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UrlMappingRepository urlMappingRepository;

	public String shortenURL(String longUrl, String userId) {

		User user = userRepository.findByEmail(userId)
				.orElseThrow(() -> new RuntimeException("User not found: " + userId));
		String shortCode = generateUniqueCode();
		UrlMapping mapping = new UrlMapping();
		mapping.setOriginalUrl(longUrl);
		mapping.setShortCode(shortCode);

		mapping.setCreatedAt(LocalDateTime.now());
		mapping.setCreatedBy(user);

		urlMappingRepository.save(mapping);
		return shortCode;
	}

	public String getLongUrl(String shortCode) {
		return urlMappingRepository.findByShortCode(shortCode).map(UrlMapping::getOriginalUrl)
				.orElseThrow(() -> new RuntimeException("URL not found"));
	}

	private String generateUniqueCode() {
		// Use secure random or UUID and hash; keep it 6-8 characters
		return UUID.randomUUID().toString().substring(0, 6);
	}
}
