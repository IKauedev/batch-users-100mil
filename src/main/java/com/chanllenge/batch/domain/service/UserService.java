package com.chanllenge.batch.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.chanllenge.batch.application.dto.SuperUserResponseDTO;
import com.chanllenge.batch.application.dto.TeamInsightDTO;
import com.chanllenge.batch.application.dto.TopCountryDTO;
import com.chanllenge.batch.domain.entity.team.Team;
import com.chanllenge.batch.domain.entity.user.User;
import com.chanllenge.batch.domain.entity.user.UserLog;
import com.chanllenge.batch.infrastructure.repository.user.UserRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	private List<User> findSuperUsers() {
		return userRepository.findAll().stream().filter(user -> Boolean.TRUE.equals(user.getActive()))
				.filter(user -> user.getScore() != null && user.getScore() >= 900).toList();
	}

	public List<TopCountryDTO> filterTopCountry() {
		Instant start = Instant.now();

		Map<String, Long> countryCounts = findSuperUsers().stream()
				.collect(Collectors.groupingBy(User::getCountry, Collectors.counting()));

		return countryCounts.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(5)
				.map(entry -> new TopCountryDTO(entry.getKey(), entry.getValue(),
						Duration.between(start, Instant.now()).toMillis()))
				.toList();
	}

	public SuperUserResponseDTO filterSuperUsers(Boolean active) {
		Instant start = Instant.now();

		List<User> filteredUsers = userRepository.findAll().stream()
				.filter(user -> Boolean.TRUE.equals(user.getActive()) == active)
				.filter(user -> user.getScore() != null && user.getScore() >= 900).toList();

		long durationMs = Duration.between(start, Instant.now()).toMillis();
		SuperUserResponseDTO response = new SuperUserResponseDTO();
		response.setSuperusers_count(filteredUsers.size());
		response.setResponse_time_ms(durationMs);
		response.setStatus(HttpStatus.OK.value());
		return response;
	}

	public List<TeamInsightDTO> getTeamInsights() {
		Instant start = Instant.now();
		List<User> users = userRepository.findAll();

		Map<String, List<User>> groupedByTeam = users.stream()
				.filter(user -> user.getTeam() != null && user.getTeam().getName() != null)
				.collect(Collectors.groupingBy(user -> user.getTeam().getName()));

		List<TeamInsightDTO> insights = groupedByTeam.entrySet().stream().map(entry -> {
			String teamName = entry.getKey();
			List<User> teamMembers = entry.getValue();
			Team team = teamMembers.get(0).getTeam();

			long totalMembers = teamMembers.size();
			long totalLeaders = Boolean.TRUE.equals(team.getLeader()) ? 1L : 0L;
			long totalProjectsCompleted = team.getProjects() != null
					? team.getProjects().stream().filter(project -> Boolean.TRUE.equals(project.getCompleted())).count()
					: 0L;
			long totalActiveMembers = teamMembers.stream().filter(user -> Boolean.TRUE.equals(user.getActive()))
					.count();

			double activePercentage = totalMembers > 0 ? (totalActiveMembers * 100.0) / totalMembers : 0.0;
			return new TeamInsightDTO(teamName, totalMembers, totalLeaders, totalProjectsCompleted, activePercentage,
					Duration.between(start, Instant.now()).toMillis());
		}).toList();
		return insights;
	}

	public List<Map.Entry<Date, Long>> getActiveUsersPerDay(Long minLogins) {
		Instant start = Instant.now();
		List<User> users = userRepository.findAll();

		List<UserLog> allLogs = users.stream()
				.flatMap(user -> user.getLogs() == null ? Stream.empty() : user.getLogs().stream()).toList();

		Map<Date, Long> loginCountsByDate = allLogs.stream().filter(log -> log.getDate() != null)
				.filter(log -> "LOGIN".equalsIgnoreCase(log.getAction()))
				.collect(Collectors.groupingBy(UserLog::getDate, Collectors.counting()));

		return loginCountsByDate.entrySet().stream().filter(entry -> minLogins == null || entry.getValue() >= minLogins)
				.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).toList();
	}
}
