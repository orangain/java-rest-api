package com.example.resource;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.ibatis.session.SqlSession;
import org.glassfish.jersey.server.ResourceConfig;

import com.example.ApiApplication;
import com.example.dto.Film;
import com.example.mapper.FilmMapper;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("films")
public class FilmResource {
	@Inject
	Application application;

	private SqlSession openSession() {
		ApiApplication application = (ApiApplication) ((ResourceConfig) this.application).getApplication();
		return application.openSession();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Film> getFilms() {
		try (SqlSession session = this.openSession()) {
			FilmMapper mapper = session.getMapper(FilmMapper.class);
			return mapper.selectFilm();
		}
	}

	@GET
	@Path("{filmId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Film.class)))
	@ApiResponse(responseCode = "404", description = "Item not found")
	public Response getFilm(@PathParam("filmId") int filmId) {
		try (SqlSession session = this.openSession()) {
			FilmMapper mapper = session.getMapper(FilmMapper.class);
			Film film = mapper.selectFilm(filmId);
			if (film == null) {
				return Response.status(Status.NOT_FOUND).build();
			}

			return Response.ok(film).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = Film.class)))
	public Response createFilm(Film film, @Context UriInfo uriInfo) {
		try (SqlSession session = this.openSession()) {
			FilmMapper mapper = session.getMapper(FilmMapper.class);
			int numAffected = mapper.insertFilmAndCollections(film);
			if (numAffected == 0) {
				return Response.serverError().entity("Failed to insert Film").build();
			}
			session.commit();
		}

		int createdFilmId = film.getFilmId();
		// Ensure to return committed result
		try (SqlSession session = this.openSession()) {
			FilmMapper mapper = session.getMapper(FilmMapper.class);
			Film createdFilm = mapper.selectFilm(createdFilmId);
			if (createdFilm == null) {
				return Response.serverError().entity("Something wrong").build();
			}

			// See: https://stackoverflow.com/a/26094619
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			URI location = builder.path(Integer.toString(createdFilmId)).build();

			return Response.created(location).entity(createdFilm).build();
		}
	}

	@PATCH
	@Path("{filmId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Film.class)))
	public Response updateFilm(Film changes, @PathParam("filmId") int filmId) {
		if (!changes.isAtLeastOneNormalFieldChanged() && changes.getActors() == null) {
			return Response.status(Status.BAD_REQUEST).entity("At least one field is required").build();
		}

		changes.setFilmId(filmId);
		try (SqlSession session = this.openSession()) {
			FilmMapper mapper = session.getMapper(FilmMapper.class);
			int numAffected = mapper.updateFilmAndCollections(changes);
			if (numAffected == 0) {
				return Response.serverError().entity("Failed to update").build();
			}

			session.commit();
		}

		// Ensure to return committed result
		try (SqlSession session = this.openSession()) {
			FilmMapper mapper = session.getMapper(FilmMapper.class);
			Film updatedFilm = mapper.selectFilm(filmId);
			if (updatedFilm == null) {
				return Response.serverError().entity("Something wrong").build();
			}

			return Response.ok(updatedFilm).build();
		}
	}

	@DELETE
	@Path("{filmId}")
	@ApiResponse(responseCode = "204", description = "Successfully deleted")
	@ApiResponse(responseCode = "404", description = "Item not found")
	public Response deleteFilm(@PathParam("filmId") int filmId) {
		try (SqlSession session = this.openSession()) {
			FilmMapper mapper = session.getMapper(FilmMapper.class);
			int numAffected = mapper.deleteFilm(filmId);
			if (numAffected == 0) {
				return Response.status(Status.NOT_FOUND).build();
			}

			session.commit();
			return Response.noContent().build();
		}
	}
}
