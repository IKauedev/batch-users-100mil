package com.chanllenge.batch.entity.project;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "projetos")
public class Project {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@JsonProperty(value = "nome")
    private String name;
	
	@JsonProperty(value = "concluido")
    private Boolean completed;
    
    public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
}