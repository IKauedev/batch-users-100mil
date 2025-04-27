package com.chanllenge.batch.application.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomerBatchController {
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job importCustomerJob;

	@GetMapping("/customer")
	public ResponseEntity<String> handle() throws JobInstanceAlreadyCompleteException,
			JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, IOException {
		ClassPathResource imgFile = new ClassPathResource("customers-100.csv");
		String pathToResource = imgFile.getFile().getAbsolutePath();

		JobParameters params = new JobParametersBuilder().addString("filePath", pathToResource)
				.addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
		this.jobLauncher.run(importCustomerJob, params);
		return ResponseEntity.ok().body("Batch job has been invoked");
	}
}
