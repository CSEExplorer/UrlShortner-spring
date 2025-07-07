package com.aditya.UrlShortner.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aditya.UrlShortner.Repository.UrlMappingRepository;
import com.aditya.UrlShortner.Repository.UserRepository;
import com.aditya.UrlShortner.dto.UrlStatsDTO;
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
		UrlMapping mapping = urlMappingRepository.findByShortCode(shortCode)
				.orElseThrow(() -> new RuntimeException("Short URL not found"));

		// ✅ Analytics updates
		mapping.setClickCount(mapping.getClickCount() + 1);
		mapping.setLastVisited(LocalDateTime.now());

		urlMappingRepository.save(mapping);

		return mapping.getOriginalUrl();
	}

	public List<UrlStatsDTO> getUserUrlStats(String userEmail) {
		// Fetch the user by email or throw if not found
		User user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

		// Get all URL mappings created by this user
		List<UrlMapping> userUrls = urlMappingRepository.findAllByCreatedBy(user);

		// Map each UrlMapping to a UrlStatsDTO
		return userUrls.stream().map(url -> new UrlStatsDTO(url.getShortCode(), url.getOriginalUrl(),
				url.getClickCount(), url.getLastVisited(), url.getCreatedAt())).toList();
	}

	private String generateUniqueCode() {
		// Use secure random or UUID and hash; keep it 6-8 characters
		return UUID.randomUUID().toString().substring(0, 6);
	}
}
