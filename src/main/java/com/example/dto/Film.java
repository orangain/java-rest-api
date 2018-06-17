package com.example.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

import org.eclipse.persistence.oxm.annotations.XmlElementNillable;
import org.eclipse.persistence.oxm.annotations.XmlWriteOnly;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

@XmlElementNillable(nillable = true)
public class Film {
	// Normal fields
	private Integer filmId;
	private String title;
	private String description;
	@QueryParam("releaseYear")
	private Integer releaseYear;
	private Integer originalLanguageId;
	@QueryParam("rentalDuration")
	private Integer rentalDuration;
	private BigDecimal rentalRate;
	private Integer length;
	private BigDecimal replacementCost;
	private String rating;
	private String specialFeatures;
	private OffsetDateTime lastUpdate;
	private LanguageInFilm language;
	private List<FilmActor> actors;

	// Normal properties
	public Integer getFilmId() {
		return filmId;
	}

	public void setFilmId(Integer filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	public Integer getOriginalLanguageId() {
		return originalLanguageId;
	}

	public void setOriginalLanguageId(Integer originalLanguageId) {
		this.originalLanguageId = originalLanguageId;
	}

	public Integer getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(Integer rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	@Schema(type = "string")
	public BigDecimal getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}

	@XmlWriteOnly // @XmlWriteOnly means read-only as an API
	@Schema(accessMode = AccessMode.READ_ONLY)
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@Schema(type = "string")
	public BigDecimal getReplacementCost() {
		return replacementCost;
	}

	public void setReplacementCost(BigDecimal replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	public OffsetDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(OffsetDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Valid
	public LanguageInFilm getLanguage() {
		return language;
	}

	public void setLanguage(LanguageInFilm languageId) {
		this.language = languageId;
	}

	public List<FilmActor> getActors() {
		return actors;
	}

	public void setActors(List<FilmActor> actors) {
		this.actors = actors;
	}

	/**
	 * Inner class to add constrains. Note: Class name must be different from
	 * {@code Language} to work with moxy's {@code MessageBodyReader}.
	 *
	 */
	public static class LanguageInFilm extends Language {

		@NotNull
		@Override
		public Integer getLanguageId() {
			return super.getLanguageId();
		}
	}
}
