package com.aditya.UrlShortner.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "url_mappings")
public class UrlMapping {

	@Id
	private String id;

	private String originalUrl;

	private String shortCode; // Unique short string like "abc123"

	private Date createdAt;

	private int clickCount;

	@DBRef
	private User createdBy; // The user who created the shortened URL
}
