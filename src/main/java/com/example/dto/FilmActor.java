package com.example.dto;

import java.time.OffsetDateTime;

import org.eclipse.persistence.oxm.annotations.XmlElementNillable;

@XmlElementNillable(nillable = true)
public class FilmActor {
	private Integer filmId;
	private OffsetDateTime lastUpdate;
	private Actor actor;

	public Integer getFilmId() {
		return filmId;
	}

	public void setFilmId(Integer filmId) {
		this.filmId = filmId;
	}

	public OffsetDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(OffsetDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
