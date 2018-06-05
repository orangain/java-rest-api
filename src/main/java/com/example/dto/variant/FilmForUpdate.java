package com.example.dto.variant;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.xml.bind.annotation.XmlTransient;

import com.example.dto.Film;
import com.example.util.DtoUtil;

import io.swagger.v3.oas.annotations.Hidden;

public class FilmForUpdate extends Film {

	// Boolean fields to distinguish provided null value from non-provided field
	// https://stackoverflow.com/questions/38424383/how-to-distinguish-between-null-and-not-provided-values-for-partial-updates-in-s
	@XmlTransient
	@Hidden
	public boolean hasTitleChanged;
	@XmlTransient
	@Hidden
	public boolean hasDescriptionChanged;
	@XmlTransient
	@Hidden
	public boolean hasReleaseYearChanged;
	@XmlTransient
	@Hidden
	public boolean hasOriginalLanguageIdChanged;
	@XmlTransient
	@Hidden
	public boolean hasLanguageIdChanged;
	@XmlTransient
	@Hidden
	public boolean hasRentalDurationChanged;
	@XmlTransient
	@Hidden
	public boolean hasRentalRateChanged;
	@XmlTransient
	@Hidden
	public boolean hasLengthChanged;
	@XmlTransient
	@Hidden
	public boolean hasReplacementCostChanged;
	@XmlTransient
	@Hidden
	public boolean hasRatingChanged;
	@XmlTransient
	@Hidden
	public boolean hasSpecialFeaturesChanged;
	@XmlTransient
	@Hidden
	public boolean hasLastUpdateChanged;

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		this.hasTitleChanged = true;
	}

	@Override
	public void setDescription(String description) {
		super.setDescription(description);
		this.hasDescriptionChanged = true;
	}

	@Override
	public void setReleaseYear(Integer releaseYear) {
		super.setReleaseYear(releaseYear);
		this.hasReleaseYearChanged = true;
	}

	@Override
	public void setLanguageId(Integer languageId) {
		super.setLanguageId(languageId);
		this.hasLanguageIdChanged = true;
	}

	@Override
	public void setOriginalLanguageId(Integer originalLanguageId) {
		super.setOriginalLanguageId(originalLanguageId);
		this.hasOriginalLanguageIdChanged = true;
	}

	@Override
	public void setRentalDuration(Integer rentalDuration) {
		super.setRentalDuration(rentalDuration);
		this.hasRentalDurationChanged = true;
	}

	@Override
	public void setRentalRate(BigDecimal rentalRate) {
		super.setRentalRate(rentalRate);
		this.hasRentalRateChanged = true;
	}

	@Override
	public void setLength(Integer length) {
		super.setLength(length);
		this.hasLengthChanged = true;
	}

	@Override
	public void setReplacementCost(BigDecimal replacementCost) {
		super.setReplacementCost(replacementCost);
		this.hasReplacementCostChanged = true;
	}

	@Override
	public void setRating(String rating) {
		super.setRating(rating);
		this.hasRatingChanged = true;
	}

	@Override
	public void setSpecialFeatures(String specialFeatures) {
		super.setSpecialFeatures(specialFeatures);
		this.hasSpecialFeaturesChanged = true;
	}

	@Override
	public void setLastUpdate(OffsetDateTime lastUpdate) {
		super.setLastUpdate(lastUpdate);
		this.hasLastUpdateChanged = true;
	}

	@Hidden
	public boolean hasAnyNonCollectionFieldChanged() {
		return DtoUtil.hasAnyNonCollectionFieldChanged(this);
	}
}
