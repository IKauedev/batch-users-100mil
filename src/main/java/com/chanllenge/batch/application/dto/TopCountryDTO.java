package com.chanllenge.batch.application.dto;


public class TopCountryDTO {
    private String country;
    private Long superuser_count;
	private Long response_time_ms;
        
    public TopCountryDTO(String country, Long superuser_count, Long response_time_ms) {
		this.country = country;
		this.superuser_count = superuser_count;
		this.response_time_ms = response_time_ms;
	}
    
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Long getSuperuser_count() {
		return superuser_count;
	}
	public void setSuperuser_count(Long superuser_count) {
		this.superuser_count = superuser_count;
	}
}