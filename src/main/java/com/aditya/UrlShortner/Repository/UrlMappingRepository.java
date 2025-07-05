package com.aditya.UrlShortner.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

//UrlMappingRepository.java

import com.aditya.UrlShortner.model.UrlMapping;

public interface UrlMappingRepository extends MongoRepository<UrlMapping, String> {
	Optional<UrlMapping> findByShortCode(String shortCode);
}
