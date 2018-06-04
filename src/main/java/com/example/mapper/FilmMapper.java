package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.dto.Film;
import com.example.dto.FilmActor;

/**
 * MyBatis mapper interface for Film.
 *
 */
public interface FilmMapper {
	/**
	 * Get a {@code Film} from database.
	 * 
	 * @param filmId
	 *            an ID of {@code Film} to get
	 * @return a Film object
	 */
	Film selectFilm(@Param("filmId") int filmId);

	/**
	 * Get all {@code Film}s from database.
	 * 
	 * @return list of {@code Film} objects
	 */
	List<Film> selectFilm();

	/**
	 * Get filtered {@code Film}s from database.
	 * 
	 * @param filter
	 *            A {@code Film} object to filter result. Modified properties of the
	 *            object are used as WHERE clause.
	 * @return list of Film object
	 */
	List<Film> selectFilm(Film filter);

	/**
	 * Insert a {@code Film} into database.
	 * 
	 * @param film
	 *            a Film object to insert
	 * @return number of affected rows
	 */
	int insertFilm(Film film);

	/**
	 * Update a {@code Film} in database.
	 * 
	 * @param changes
	 *            A {@code Film} object to represent changes. Only modified
	 *            properties of the object are changed.
	 * @return number of affected rows
	 */
	int updateFilm(Film changes);

	/**
	 * Delete a {@code Film} from database.
	 * 
	 * @param filmId
	 *            an ID of {@code Film} to delete
	 * @return number of affected rows
	 */
	int deleteFilm(@Param("filmId") int filmId);

	/**
	 * Insert {@code FilmActor}s into database.
	 * 
	 * @param filmActors
	 *            list of {@code FilmActor}s to insert
	 * @return number of affected rows
	 */
	int insertFilmActors(List<FilmActor> filmActors);

	/**
	 * Delete {@code FilmActor}s associated with a Film from database.
	 * 
	 * @param filmId
	 *            An ID of {@code Film}. All {@code FilmActor}s associated with the
	 *            {@code Film} are deleted.
	 * @return number of affected rows
	 */
	int deleteFilmActors(int filmId);

	/**
	 * Insert a {@code Film} and its collections into database.
	 * 
	 * @param film
	 *            a {@code Film} to insert
	 * @return number of affected rows
	 */
	default int insertFilmAndCollections(Film film) {
		// Film
		int numAffected = this.insertFilm(film);
		if (numAffected == 0) {
			return numAffected; // Failed to insert
		}
		int generatedId = film.getFilmId();

		// FilmActors
		if (film.getActors().size() > 0) {
			film.getActors().forEach(fa -> fa.setFilmId(generatedId));
			numAffected += this.insertFilmActors(film.getActors());
		}

		return numAffected;
	}

	/**
	 * Update a {@code Film} including its collections.
	 * 
	 * @param changes
	 *            A {@code Film} object to represent changes. Only modified
	 *            properties of the object are changed. When it contains a
	 *            collection, the collection will be updated by DELETE and INSERT
	 *            strategy.
	 * @return number of affected rows
	 */
	default int updateFilmAndCollections(Film changes) {
		int numAffected = 0;
		// Film
		if (changes.isAtLeastOneNormalFieldChanged()) {
			numAffected = this.updateFilm(changes);
			if (numAffected == 0) {
				return numAffected; // Failed to insert
			}
		}

		// FilmActors: delete and insert
		if (changes.getActors() != null) {
			numAffected += this.deleteFilmActors(changes.getFilmId());
			if (changes.getActors().size() > 0) {
				changes.getActors().forEach(fa -> fa.setFilmId(changes.getFilmId()));
				numAffected += this.insertFilmActors(changes.getActors());
			}
		}

		return numAffected;
	}
}
