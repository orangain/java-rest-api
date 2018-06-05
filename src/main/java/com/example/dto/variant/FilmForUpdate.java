package com.example.dto.variant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlTransient;

import com.example.dto.Film;

import io.swagger.v3.oas.annotations.Hidden;

public class FilmForUpdate extends Film {

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

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		this.isTitleChanged = true;
	}

	@Override
	public void setDescription(String description) {
		super.setDescription(description);
		this.isDescriptionChanged = true;
	}

	@Override
	public void setReleaseYear(Integer releaseYear) {
		super.setReleaseYear(releaseYear);
		this.isReleaseYearChanged = true;
	}

	@Override
	public void setLanguageId(Integer languageId) {
		super.setLanguageId(languageId);
		this.isLanguageIdChanged = true;
	}

	@Override
	public void setOriginalLanguageId(Integer originalLanguageId) {
		super.setOriginalLanguageId(originalLanguageId);
		this.isOriginalLanguageIdChanged = true;
	}

	@Override
	public void setRentalDuration(Integer rentalDuration) {
		super.setRentalDuration(rentalDuration);
		this.isRentalDurationChanged = true;
	}

	@Override
	public void setRentalRate(BigDecimal rentalRate) {
		super.setRentalRate(rentalRate);
		this.isRentalRateChanged = true;
	}

	@Override
	public void setLength(Integer length) {
		super.setLength(length);
		this.isLengthChanged = true;
	}

	@Override
	public void setReplacementCost(BigDecimal replacementCost) {
		super.setReplacementCost(replacementCost);
		this.isReplacementCostChanged = true;
	}

	@Override
	public void setRating(String rating) {
		super.setRating(rating);
		this.isRatingChanged = true;
	}

	@Override
	public void setSpecialFeatures(String specialFeatures) {
		super.setSpecialFeatures(specialFeatures);
		this.isSpecialFeaturesChanged = true;
	}

	@Override
	public void setLastUpdate(OffsetDateTime lastUpdate) {
		super.setLastUpdate(lastUpdate);
		this.isLastUpdateChanged = true;
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
