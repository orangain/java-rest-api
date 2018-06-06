package com.example.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.ibatis.session.SqlSession;

import com.example.dao.FilmDao;
import com.example.dto.Film;
import com.example.dto.variant.FilmForCreate;
import com.example.dto.variant.FilmForUpdate;
import com.example.sqlmapper.FilmMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("films")
public class FilmResource extends BaseResource {

	private FilmDao getDao(SqlSession session) {
		return new FilmDao(session.getMapper(FilmMapper.class));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Films", tags = { "Film" })
	public List<Film> getFilms() {
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			return dao.getFilms();
		}
	}

	@GET
	@Path("{filmId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get a Film", tags = { "Film" })
	@ApiResponse(responseCode = "200", description = "Item successfully found")
	@ApiResponse(responseCode = "404", description = "Item not found")
	public Film getFilm(@PathParam("filmId") int filmId) {
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			Film film = dao.getFilm(filmId);
			if (film == null) {
				throw new WebApplicationException(Status.NOT_FOUND);
			}

			return film;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Create a Film", tags = { "Film" })
	@ApiResponse(responseCode = "201", description = "Item successfully created", content = @Content(schema = @Schema(implementation = Film.class)))
	@ApiResponse(responseCode = "400", description = "Validation error")
	public Response createFilm(@Valid FilmForCreate film, @Context UriInfo uriInfo) {
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			int numAffected = dao.insertFilm(film);
			if (numAffected == 0) {
				throw new WebApplicationException("Failed to insert");
			}
			session.commit();
		}

		int createdFilmId = film.getFilmId();
		// Ensure to return committed result
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			Film createdFilm = dao.getFilm(createdFilmId);
			if (createdFilm == null) {
				throw new WebApplicationException("Something wrong");
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
	@Operation(summary = "Update a Film", tags = { "Film" })
	@ApiResponse(responseCode = "200", description = "Item successfully updated")
	@ApiResponse(responseCode = "400", description = "Validation error")
	@ApiResponse(responseCode = "404", description = "Item not found")
	public Film updateFilm(@Valid FilmForUpdate changes, @PathParam("filmId") int filmId) {
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			int numAffected = dao.updateFilm(filmId, changes);
			if (numAffected == 0) {
				throw new WebApplicationException(Status.NOT_FOUND);
			}

			session.commit();
		}

		// Ensure to return committed result
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			Film updatedFilm = dao.getFilm(filmId);
			if (updatedFilm == null) {
				throw new WebApplicationException("Something wrong");
			}

			return updatedFilm;
		}
	}

	@DELETE
	@Path("{filmId}")
	@Operation(summary = "Delete a Film", tags = { "Film" })
	@ApiResponse(responseCode = "204", description = "Item successfully deleted")
	@ApiResponse(responseCode = "404", description = "Item not found")
	public Response deleteFilm(@PathParam("filmId") int filmId) {
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			int numAffected = dao.deleteFilm(filmId);
			if (numAffected == 0) {
				throw new WebApplicationException(Status.NOT_FOUND);
			}

			session.commit();
			return Response.noContent().build();
		}
	}
}
