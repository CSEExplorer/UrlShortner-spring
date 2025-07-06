package com.aditya.UrlShortner.Controller;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.UrlShortner.Service.URLShortenerService;

@RestController
@RequestMapping("/api/url")
public class URLShortenerController {

	@Autowired
	private URLShortenerService urlShortenerService;

	@PostMapping("/shorten")
	public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request, Principal principal) {
		String longUrl = request.get("longUrl");
		String userId = principal.getName();

		String shortCode = urlShortenerService.shortenURL(longUrl, userId);
		return ResponseEntity.ok(Map.of("shortUrl", "http://localhost:8080/api/url/" + shortCode));
	}

	@GetMapping("/{shortCode}")
	public ResponseEntity<?> redirectToOriginal(@PathVariable String shortCode) {
		String longUrl = urlShortenerService.getLongUrl(shortCode);
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(longUrl)).build();
	}
}
