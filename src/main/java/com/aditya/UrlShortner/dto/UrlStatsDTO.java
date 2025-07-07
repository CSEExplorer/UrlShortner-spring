package com.aditya.UrlShortner.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlStatsDTO {
	private String shortCode;
	private String originalUrl;
	private int clickCount;
	private LocalDateTime lastVisited;
	private LocalDateTime createdAt;
}
