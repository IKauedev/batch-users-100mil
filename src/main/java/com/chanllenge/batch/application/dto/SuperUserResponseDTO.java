package com.chanllenge.batch.application.dto;

public class SuperUserResponseDTO {
    private int superusers_count;
    private long response_time_ms;
	private int status;
	
    public int getSuperusers_count() {
		return superusers_count;
	}
	
	public void setSuperusers_count(int superusers_count) {
		this.superusers_count = superusers_count;
	}
	
	public long getResponse_time_ms() {
		return response_time_ms;
	}
	
	public void setResponse_time_ms(long response_time_ms) {
		this.response_time_ms = response_time_ms;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}