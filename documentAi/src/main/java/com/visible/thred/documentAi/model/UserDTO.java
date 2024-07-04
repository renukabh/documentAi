package com.visible.thred.documentAi.model;

import java.time.LocalDateTime;
import java.util.Set;

public class UserDTO {

	private String email;
	private LocalDateTime dateAdded;
	 private Set<Long> teamIds;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(LocalDateTime dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Set<Long> getTeamIds() {
		return teamIds;
	}
	public void setTeamIds(Set<Long> teamIds) {
		this.teamIds = teamIds;
	}
	
}
