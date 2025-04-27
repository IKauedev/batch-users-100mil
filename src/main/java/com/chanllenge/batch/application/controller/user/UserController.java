package com.chanllenge.batch.application.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chanllenge.batch.application.builder.ResponseBuilder;
import com.chanllenge.batch.application.dto.ResponseDTO;
import com.chanllenge.batch.application.dto.SuperUserResponseDTO;
import com.chanllenge.batch.domain.entity.user.User;
import com.chanllenge.batch.domain.service.UserService;
import com.chanllenge.batch.infrastructure.repository.user.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {
	@Autowired
	private final JobLauncher jobLauncher;
    private final UserService userService;
    private final Job importUserJob;
    private final ObjectMapper objectMapper;
    
    public UserController(JobLauncher jobLauncher, Job importUserJob, ObjectMapper objectMapper, UserService userService) {
		this.jobLauncher = jobLauncher;
		this.importUserJob = importUserJob;
		this.objectMapper = objectMapper;
		this.userService = userService;
	}

	@PostMapping("/users")
    public ResponseEntity<ResponseDTO> uploadJsonFileAndProcess(@RequestBody byte[] fileContent) throws Exception {
        Instant start = Instant.now();
        if (fileContent.length == 0) {
            return ResponseBuilder.buildErrorResponse();
        }

        JsonNode jsonNode = parseJson(fileContent);
        File tempFile = saveTempFile(jsonNode.toString());
        try {
            JobExecution jobExecution = startJob(tempFile.getAbsolutePath());
            return ResponseBuilder.buildSuccessResponse(jobExecution, start);
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
	
	@GetMapping("/super-users")
	public ResponseEntity<SuperUserResponseDTO> filterSuperUsers(
	        @RequestParam(value = "active", defaultValue = "true") boolean active
	) {
	    try {
	        SuperUserResponseDTO response = userService.filterSuperUsers(active);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        log.error("Erro ao filtrar superusuários: {}", e.getMessage(), e);
	        SuperUserResponseDTO errorResponse = new SuperUserResponseDTO();
	        errorResponse.setSuperusers_count(0);
	        errorResponse.setResponse_time_ms(0L);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

    private JsonNode parseJson(byte[] content) throws IOException {
        try {
            return objectMapper.readTree(new String(content, StandardCharsets.UTF_8));
        } catch (Exception e) {
        	log.info("Arquivo não é um JSON válido" + e.getMessage().toString());
            throw new IllegalArgumentException("Arquivo não é um JSON válido: " + e.getMessage(), e);
        }
    }

    private File saveTempFile(String content) throws IOException {
        File tempFile = File.createTempFile("uploaded-", ".json");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        }
        return tempFile;
    }

    private JobExecution startJob(String filePath) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("filePath", filePath)
                .addLong("JobID", System.currentTimeMillis())
                .toJobParameters();
        return jobLauncher.run(importUserJob, params);
    }
}
