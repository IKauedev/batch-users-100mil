package com.chanllenge.batch.domain.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import com.chanllenge.batch.domain.entity.team.Team;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@JsonRootName(value = "usuarios")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@JsonProperty(value = "nome")
	private String name;

	@JsonProperty(value = "idade")
	private Integer age;

	@JsonProperty(value = "score")
	private Integer score;

	@JsonProperty(value = "ativo")
	private Boolean active;

	@JsonProperty(value = "pais")
	private String country;

	private String status;

	@Embedded
	@JsonProperty(value = "equipe")
	@AttributeOverrides({ @AttributeOverride(name = "name", column = @Column(name = "team_name")),
			@AttributeOverride(name = "leader", column = @Column(name = "team_leader")) })
	private Team team;

	@JsonProperty(value = "logs")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private List<UserLog> logs;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<UserLog> getLogs() {
		return logs;
	}

	public void setLogs(List<UserLog> logs) {
		this.logs = logs;
	}
}
