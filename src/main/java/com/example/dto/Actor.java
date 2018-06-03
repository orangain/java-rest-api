package com.example.dto;

import java.time.OffsetDateTime;

import org.eclipse.persistence.oxm.annotations.XmlElementNillable;

@XmlElementNillable(nillable = true)
public class Actor {
	private Integer actorId;
	private String firstName;
	private String lastName;
	private OffsetDateTime lastUpdate;

	public Integer getActorId() {
		return actorId;
	}

	public void setActorId(Integer actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public OffsetDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(OffsetDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
