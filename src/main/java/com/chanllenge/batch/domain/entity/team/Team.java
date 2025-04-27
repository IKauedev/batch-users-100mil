package com.chanllenge.batch.domain.entity.team;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import com.chanllenge.batch.domain.entity.project.Project;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "time")
public class Team {	
	@Id
    private UUID id;

	@JsonProperty(value = "nome")
    private String name;
	
	@JsonProperty(value = "lider")
	private Boolean leader;

    @JsonProperty("projetos")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getLeader() {
		return leader;
	}

	public void setLeader(Boolean leader) {
		this.leader = leader;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
}