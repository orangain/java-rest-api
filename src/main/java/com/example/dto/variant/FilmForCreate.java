package com.example.dto.variant;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.example.dto.Film;

public class FilmForCreate extends Film {

	// Override getters of required fields to add @NotNull annotation
	@NotNull
	@Override
	public String getTitle() {
		return super.getTitle();
	}

	@NotNull
	@Override
	public Integer getLanguageId() {
		return super.getLanguageId();
	}

	@NotNull
	@Override
	public Integer getRentalDuration() {
		return super.getRentalDuration();
	}

	@NotNull
	@Override
	public BigDecimal getRentalRate() {
		return super.getRentalRate();
	}

	@NotNull
	@Override
	public BigDecimal getReplacementCost() {
		return super.getReplacementCost();
	}
}
