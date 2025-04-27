package com.chanllenge.batch.application.controller.countries;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanllenge.batch.application.dto.TopCountryDTO;
import com.chanllenge.batch.domain.entity.user.User;
import com.chanllenge.batch.domain.service.UserService;
import com.chanllenge.batch.infrastructure.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CountriesController {
	@Autowired
	private UserService userService;

	@GetMapping("/top-countries")
	public ResponseEntity<List<TopCountryDTO>> getTopCountries() {
		List<TopCountryDTO> topCountry = userService.filterTopCountry();
		return ResponseEntity.ok(topCountry);
	}
}
