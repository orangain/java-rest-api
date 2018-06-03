package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.dto.Film;
import com.example.dto.FilmActor;

public interface FilmMapper {
	Film selectFilm(@Param("filmId") int filmId);

	List<Film> selectFilm();

	List<Film> selectFilm(Film filter);

	int insertFilm(Film film);

	int updateFilm(Film changes);

	int deleteFilm(@Param("filmId") int filmId);

	int insertFilmActors(List<FilmActor> filmActors);

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
}
