package com.chanllenge.batch.domain.service;

import com.chanllenge.batch.application.dto.SuperUserResponseDTO;
import com.chanllenge.batch.application.dto.TopCountryDTO;
import com.chanllenge.batch.domain.entity.user.User;
import com.chanllenge.batch.infrastructure.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
	@Autowired
    private UserRepository userRepository;

    private List<User> findSuperUsers() {
        return userRepository.findAll().stream()
                .filter(user -> Boolean.TRUE.equals(user.getActive()))
                .filter(user -> user.getScore() != null && user.getScore() >= 900)
                .toList();
    }

    public List<TopCountryDTO> filterTopCountry() {
        Instant start = Instant.now();
        
        Map<String, Long> countryCounts = findSuperUsers().stream()
                .collect(Collectors.groupingBy(User::getCountry, Collectors.counting()));

        return countryCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(entry -> new TopCountryDTO(entry.getKey(), entry.getValue(), Duration.between(start, Instant.now()).toMillis()))
                .toList();
    }

    public SuperUserResponseDTO filterSuperUsers(Boolean active) {
        Instant start = Instant.now();

        List<User> filteredUsers = userRepository.findAll().stream()
                .filter(user -> Boolean.TRUE.equals(user.getActive()) == active)
                .filter(user -> user.getScore() != null && user.getScore() >= 900)
                .toList();

        long durationMs = Duration.between(start, Instant.now()).toMillis();
        SuperUserResponseDTO response = new SuperUserResponseDTO();
        response.setSuperusers_count(filteredUsers.size());
        response.setResponse_time_ms(durationMs);
        response.setStatus(HttpStatus.OK.value());

        return response;
    }
}
