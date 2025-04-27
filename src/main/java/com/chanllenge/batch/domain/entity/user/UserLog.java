package com.chanllenge.batch.domain.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "logs")
public class UserLog {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @JsonProperty(value = "data")
    private Date date;
    
    @JsonProperty(value = "acao")
    private String action;
    
    public UUID getId() {
		return id;
	}
    
	public void setId(UUID id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
}