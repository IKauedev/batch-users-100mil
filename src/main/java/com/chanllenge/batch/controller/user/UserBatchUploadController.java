package com.chanllenge.batch.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserBatchUploadController {
	
	@Autowired
    private JobLauncher jobLauncher;
    
    @Autowired
    private Job importUserJob;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> uploadJsonFileAndProcess(
            @RequestBody byte[] fileContent
    ) throws IOException, 
             JobInstanceAlreadyCompleteException,
             JobExecutionAlreadyRunningException,
             JobRestartException,
             JobParametersInvalidException,
             InterruptedException {

        long startTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();

        if (fileContent.length == 0) {
            response.put("timestamp", Instant.now().toString());
            response.put("processing_time_ms", 0);
            response.put("status", "FAILED");
            response.put("message", "Nenhum arquivo enviado.");
            return ResponseEntity.badRequest().body(response);
        }

        String jsonString = new String(fileContent, StandardCharsets.UTF_8);
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(jsonString);
        } catch (Exception e) {
            response.put("timestamp", Instant.now().toString());
            response.put("processing_time_ms", 0);
            response.put("status", "FAILED");
            response.put("message", "Arquivo não é um JSON válido: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        File tempFile = File.createTempFile("uploaded-", ".json");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(jsonString.getBytes(StandardCharsets.UTF_8));
        }

        JobParameters params = new JobParametersBuilder()
                .addString("filePath", tempFile.getAbsolutePath())
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        JobExecution jobExecution = this.jobLauncher.run(importUserJob, params);
        while (jobExecution.isRunning()) {
            log.info("Batch ainda está processando...");
            Thread.sleep(500);
        }

        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;

        response.put("timestamp", Instant.now().toString());
        response.put("processing_time_ms", processingTime);
        response.put("batch_status", jobExecution.getStatus().toString());
        response.put("message", 
            jobExecution.getStatus().isUnsuccessful() 
            ? "Batch finalizado com falha." 
            : "Batch finalizado com sucesso!"
        );

        if (jobExecution.getStatus().isUnsuccessful()) {
            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.ok(response);
    }
}

