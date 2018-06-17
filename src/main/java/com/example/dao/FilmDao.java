package com.example.dao;

import java.util.List;

import com.example.dto.Film;
import com.example.dto.variant.FilmForUpdate;
import com.example.sqlmapper.FilmMapper;

public class FilmDao {

	private FilmMapper mapper;

	public FilmDao(FilmMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * Get a {@code Film} from database.
	 * 
	 * @param filmId
	 *            an ID of {@code Film} to get
	 * @return a {@code Film} object
	 */
	public Film getFilm(Integer filmId) {
		return mapper.selectFilm(filmId);
	}

	/**
	 * Get all {@code Film}s from database.
	 * 
	 * @return list of {@code Film} objects
	 */
	public List<Film> getFilms() {
		return this.getFilms(null, null);
	}

	/**
	 * Get filtered {@code Film}s from database.
	 * 
	 * @param filter
	 *            A {@code Film} object to filter result. Modified properties of the
	 *            object are used as WHERE clause.
	 * @return list of {@code Film} object
	 */
	public List<Film> getFilms(Film filter, String sort) {
		return mapper.selectFilms(filter, sort);
	}

	/**
	 * Insert a {@code Film} and its collections into database.
	 * 
	 * @param item
	 *            a {@code Film} to insert
	 * @return number of affected rows
	 */
	public int insertFilm(Film item) {
		// Film
		int numAffected = mapper.insertFilm(item);
		if (numAffected == 0) {
			return numAffected; // Failed to insert
		}
		int generatedId = item.getFilmId();

		// FilmActors
		if (item.getActors().size() > 0) {
			item.getActors().forEach(fa -> fa.setFilmId(generatedId));
			numAffected += mapper.insertFilmActors(item.getActors());
		}

		return numAffected;
	}

	/**
	 * Update a {@code Film} including its collections.
	 * 
	 * @param filmId
	 *            An ID of {@code Film}.
	 * @param changes
	 *            A {@code FilmForUpdate} object to represent changes. Only modified
	 *            properties of the object are changed. When it contains a
	 *            collection, the collection will be updated by DELETE and INSERT
	 *            strategy.
	 * @return number of affected rows
	 */
	public int updateFilm(Integer filmId, FilmForUpdate changes) {
		changes.setFilmId(filmId);

		int numAffected = 0;
		// Film
		if (changes.hasAnyNonCollectionFieldChanged()) {
			numAffected = mapper.updateFilm(changes);
			if (numAffected == 0) {
				return numAffected; // Failed to insert
			}
		}

		// FilmActors: delete and insert
		if (changes.getActors() != null) {
			numAffected += mapper.deleteFilmActors(changes.getFilmId());
			if (changes.getActors().size() > 0) {
				changes.getActors().forEach(fa -> fa.setFilmId(changes.getFilmId()));
				numAffected += mapper.insertFilmActors(changes.getActors());
			}
		}

		return numAffected;
	}

	public int deleteFilm(Integer filmId) {
		return mapper.deleteFilm(filmId);
	}
}
