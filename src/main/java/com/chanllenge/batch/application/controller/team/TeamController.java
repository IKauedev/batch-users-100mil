package com.chanllenge.batch.application.controller.team;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chanllenge.batch.application.dto.TeamInsightDTO;
import com.chanllenge.batch.application.dto.TopCountryDTO;
import com.chanllenge.batch.domain.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TeamController {
	@Autowired
	private UserService userService;

	@GetMapping("/team-insights")
	public ResponseEntity<List<TeamInsightDTO>> getTopCountries() {
		List<TeamInsightDTO> team = userService.getTeamInsights();
		return ResponseEntity.ok(team);
	}
}
