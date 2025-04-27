package com.chanllenge.batch.application.builder;

import java.time.Duration;
import java.time.Instant;

import org.springframework.batch.core.JobExecution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chanllenge.batch.application.dto.ResponseDTO;

public class ResponseBuilder {
	public static ResponseEntity<ResponseDTO> buildSuccessResponse(JobExecution execution, Instant start) {
		ResponseDTO response = new ResponseDTO();
		response.setResponse_time_ms(Duration.between(start, Instant.now()).toMillis());
		response.setValid_json(true);

		if (execution.getStatus().isUnsuccessful()) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		response.setStatus(HttpStatus.OK.value());
		return ResponseEntity.ok(response);
	}

	public static ResponseEntity<ResponseDTO> buildErrorResponse() {
		ResponseDTO response = new ResponseDTO();
		response.setResponse_time_ms(Duration.between(Instant.now(), Instant.now()).toMillis());
		response.setValid_json(false);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.badRequest().body(response);
	}
}
