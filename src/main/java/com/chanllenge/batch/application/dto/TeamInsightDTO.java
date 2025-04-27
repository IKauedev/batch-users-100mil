package com.chanllenge.batch.application.dto;

public class TeamInsightDTO {
	private String team_name;
	private long total_members;
	private long total_leaders;
	private long total_projects_completed;
	private double active_percentage;
	private Long response_time;

	public TeamInsightDTO(String team_name, long total_members, long total_leaders, long total_projects_completed,
			double active_percentage, Long response_time) {
		this.team_name = team_name;
		this.total_members = total_members;
		this.total_leaders = total_leaders;
		this.total_projects_completed = total_projects_completed;
		this.active_percentage = active_percentage;
		this.response_time = response_time;
	}

	public Long getResponse_time() {
		return response_time;
	}

	public void setResponse_time(Long response_time) {
		this.response_time = response_time;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public long getTotal_members() {
		return total_members;
	}

	public void setTotal_members(long total_members) {
		this.total_members = total_members;
	}

	public long getTotal_leaders() {
		return total_leaders;
	}

	public void setTotal_leaders(long total_leaders) {
		this.total_leaders = total_leaders;
	}

	public long getTotal_projects_completed() {
		return total_projects_completed;
	}

	public void setTotal_projects_completed(long total_projects_completed) {
		this.total_projects_completed = total_projects_completed;
	}

	public double getActive_percentage() {
		return active_percentage;
	}

	public void setActive_percentage(double active_percentage) {
		this.active_percentage = active_percentage;
	}

	public void TeamDTO() {
	}
}