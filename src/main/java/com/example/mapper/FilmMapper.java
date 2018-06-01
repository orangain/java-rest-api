package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.dto.Film;

public interface FilmMapper {
	Film selectFilm(@Param("filmId") int filmId);

	List<Film> selectFilm();

	List<Film> selectFilm(Film filter);

	int insertFilm(Film film);

	int updateFilm(Film changes);

	int deleteFilm(@Param("filmId") int filmId);
}
