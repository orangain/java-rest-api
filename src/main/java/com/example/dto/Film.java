package com.example.dto;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlElementNillable;

import io.swagger.v3.oas.annotations.Hidden;

@XmlElementNillable(nillable = true)
public class Film {
	// Normal fields
	private Integer filmId;
	private String title;
	private String description;
	private Integer releaseYear;
	private Integer languageId;
	private Integer originalLanguageId;
	private Integer rentalDuration;
	private BigDecimal rentalRate;
	private Integer length;
	private BigDecimal replacementCost;
	private String rating;
	private String specialFeatures;
	private OffsetDateTime lastUpdate;
	private List<FilmActor> actors;

	// Boolean fields to distinguish provided null value from non-provided field
	// https://stackoverflow.com/questions/38424383/how-to-distinguish-between-null-and-not-provided-values-for-partial-updates-in-s
	@XmlTransient
	@Hidden
	public boolean isTitleChanged;
	@XmlTransient
	@Hidden
	public boolean isDescriptionChanged;
	@XmlTransient
	@Hidden
	public boolean isReleaseYearChanged;
	@XmlTransient
	@Hidden
	public boolean isOriginalLanguageIdChanged;
	@XmlTransient
	@Hidden
	public boolean isLanguageIdChanged;
	@XmlTransient
	@Hidden
	public boolean isRentalDurationChanged;
	@XmlTransient
	@Hidden
	public boolean isRentalRateChanged;
	@XmlTransient
	@Hidden
	public boolean isLengthChanged;
	@XmlTransient
	@Hidden
	public boolean isReplacementCostChanged;
	@XmlTransient
	@Hidden
	public boolean isRatingChanged;
	@XmlTransient
	@Hidden
	public boolean isSpecialFeaturesChanged;
	@XmlTransient
	@Hidden
	public boolean isLastUpdateChanged;

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
		this.isTitleChanged = true;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		this.isDescriptionChanged = true;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
		this.isReleaseYearChanged = true;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
		this.isLanguageIdChanged = true;
	}

	public Integer getOriginalLanguageId() {
		return originalLanguageId;
	}

	public void setOriginalLanguageId(Integer originalLanguageId) {
		this.originalLanguageId = originalLanguageId;
		this.isOriginalLanguageIdChanged = true;
	}

	public Integer getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(Integer rentalDuration) {
		this.rentalDuration = rentalDuration;
		this.isRentalDurationChanged = true;
	}

	public BigDecimal getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
		this.isRentalRateChanged = true;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
		this.isLengthChanged = true;
	}

	public BigDecimal getReplacementCost() {
		return replacementCost;
	}

	public void setReplacementCost(BigDecimal replacementCost) {
		this.replacementCost = replacementCost;
		this.isReplacementCostChanged = true;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
		this.isRatingChanged = true;
	}

	public String getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
		this.isSpecialFeaturesChanged = true;
	}

	public OffsetDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(OffsetDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
		this.isLastUpdateChanged = true;
	}

	public List<FilmActor> getActors() {
		return actors;
	}

	public void setActors(List<FilmActor> actors) {
		this.actors = actors;
	}

	@Hidden
	public boolean isAtLeastOneNormalFieldChanged() {
		Class<? extends Film> c = this.getClass();
		Field[] fields = c.getDeclaredFields();
		return Arrays.stream(fields).filter(f -> Modifier.isPublic(f.getModifiers()))
				.filter(f -> f.getName().matches("^is.+Changed")).filter(f -> f.getType().equals(boolean.class))
				.anyMatch(f -> {
					try {
						return f.getBoolean(this);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				});
	}
}
