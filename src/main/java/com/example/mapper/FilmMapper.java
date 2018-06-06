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
	 * @return list of {@code Film} object
	 */
	List<Film> selectFilm(Film filter);

	/**
	 * Insert a {@code Film} into database.
	 * 
	 * @param item
	 *            a {@code Film} object to insert
	 * @return number of affected rows
	 */
	int insertFilm(Film item);

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
	 * @param items
	 *            list of {@code FilmActor}s to insert
	 * @return number of affected rows
	 */
	int insertFilmActors(List<FilmActor> items);

	/**
	 * Delete {@code FilmActor}s associated with a Film from database.
	 * 
	 * @param filmId
	 *            An ID of {@code Film}. All {@code FilmActor}s associated with the
	 *            {@code Film} are deleted.
	 * @return number of affected rows
	 */
	int deleteFilmActors(int filmId);
}
