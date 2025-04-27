package com.chanllenge.batch.application.dto;

import java.time.Duration;
import java.time.Instant;

public class ResponseDTO {
	private int status;
	private Boolean valid_json;
	private Long response_time_ms;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Boolean getValid_json() {
		return valid_json;
	}

	public void setValid_json(Boolean valid_json) {
		this.valid_json = valid_json;
	}

	public Long getResponse_time_ms() {
		return response_time_ms;
	}

	public void setResponse_time_ms(Long response_time_ms) {
		this.response_time_ms = response_time_ms;
	}
}
