package com.learning.orm.domain;

import java.util.*;

public class Events {

	private Long id;
	private String title;
	private Date date;
	
	private Set participants = new HashSet();

	public Events() {}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set getParticipants() {
		return participants;
	}

	public void setParticipants(Set participants) {
		this.participants = participants;
	}

}
