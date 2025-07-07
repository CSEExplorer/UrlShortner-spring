package com.aditya.UrlShortner.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

//UrlMappingRepository.java

import com.aditya.UrlShortner.model.UrlMapping;
import com.aditya.UrlShortner.model.User;

public interface UrlMappingRepository extends MongoRepository<UrlMapping, String> {
	Optional<UrlMapping> findByShortCode(String shortCode);

	List<UrlMapping> findAllByCreatedBy(User createdBy);
}
