package com.example.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NByteArrayEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.glassfish.jersey.message.MessageBodyWorkers;

import com.example.dao.FilmDao;
import com.example.dto.Film;
import com.example.dto.Film.LanguageInFilm;
import com.example.dto.variant.FilmForCreate;
import com.example.dto.variant.FilmForUpdate;
import com.example.sqlmapper.FilmMapper;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("films")
public class FilmResource extends BaseResource {
	static final String ES_INDEX = "sakila";
	static final String ES_TYPE = "film";

	private FilmDao getDao(SqlSession session) {
		return new FilmDao(session.getMapper(FilmMapper.class));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Films", tags = { "Film" })
	public List<Film> getFilms(@BeanParam Film filter, @QueryParam("language") Integer languageId,
			@QueryParam("_sort") String sort) {
		if (languageId != null) {
			if (filter == null) {
				filter = new Film();
			}
			if (filter.getLanguage() == null) {
				LanguageInFilm language = new LanguageInFilm();
				filter.setLanguage(language);
			}
			filter.getLanguage().setLanguageId(languageId);
		}

		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			return dao.getFilms(filter, sort);
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
				throw new NotFoundException();
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
				throw new InternalServerErrorException("Failed to insert");
			}
			session.commit();
		}

		int createdFilmId = film.getFilmId();
		// Ensure to return committed result
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			Film createdFilm = dao.getFilm(createdFilmId);
			if (createdFilm == null) {
				throw new InternalServerErrorException("Something wrong");
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
				throw new NotFoundException();
			}

			session.commit();
		}

		// Ensure to return committed result
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			Film updatedFilm = dao.getFilm(filmId);
			if (updatedFilm == null) {
				throw new InternalServerErrorException("Something wrong");
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
				throw new NotFoundException();
			}

			session.commit();
			return Response.noContent().build();
		}
	}

	@POST
	@Path("_echo")
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Echo back request body", tags = { "Film" })
	public byte[] echo(byte[] body) {
		return body;
	}

	@POST
	@Path("_search")
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Search films", tags = { "Film" })
	public Response search(byte[] body) {
		RestClient restClient = this.getEsClient().getLowLevelClient();
		Map<String, String> params = Collections.singletonMap("pretty", "true");
		HttpEntity entity = new NByteArrayEntity(body, ContentType.APPLICATION_JSON);
		String path = "/" + ES_INDEX + "/" + ES_TYPE + "/_search";
		try {
			org.elasticsearch.client.Response response;
			try {
				response = restClient.performRequest("POST", path, params, entity);
			} catch (ResponseException e) {
				response = e.getResponse();
			}

			int status = response.getStatusLine().getStatusCode();
			byte[] responseJson = EntityUtils.toByteArray(response.getEntity());
			return Response.status(status).entity(responseJson).build();
		} catch (IOException e) {
			throw new InternalServerErrorException(e);
		}
	}

	@POST
	@Path("_index")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Index all films", tags = { "Film" })
	public String index(@Context MessageBodyWorkers workers) {
		final MessageBodyWriter<Film> messageBodyWriter = workers.getMessageBodyWriter(Film.class, Film.class,
				new Annotation[] {}, MediaType.APPLICATION_JSON_TYPE);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BulkRequest request = new BulkRequest();

		List<Film> films;
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			films = dao.getFilms();
		}

		for (Film film : films) {
			try {
				outputStream.reset();
				messageBodyWriter.writeTo(film, Film.class, Film.class, new Annotation[] {},
						MediaType.APPLICATION_JSON_TYPE, new MultivaluedHashMap<String, Object>(), outputStream);
				request.add(new IndexRequest(ES_INDEX, ES_TYPE, film.getFilmId().toString())
						.source(outputStream.toString(StandardCharsets.UTF_8.name()), XContentType.JSON));
			} catch (IOException e) {
				throw new InternalServerErrorException(e);
			}
		}

		BulkResponse bulkResponse;
		try {
			bulkResponse = this.getEsClient().bulk(request);
		} catch (IOException e) {
			throw new InternalServerErrorException(e);
		}

		int failed = 0;
		int succeeded = 0;
		for (BulkItemResponse bulkItemResponse : bulkResponse) {
			if (bulkItemResponse.isFailed()) {
				failed++;
			} else {
				succeeded++;
			}
		}

		return String.format("{\"succeeded\": %d, \"failed\": %d}", succeeded, failed);
	}

	@GET
	@Path("_feed")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	@Operation(summary = "Atom feed", tags = { "Film" })
	public String feed() {
		List<Film> films;
		try (SqlSession session = this.openSession()) {
			FilmDao dao = this.getDao(session);
			films = dao.getFilms();
		}

		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("atom_1.0");
		feed.setTitle("Latest films");
		feed.setDescription("List of latest films");
		feed.setLink("https://example.com");

		List<SyndEntry> entries = films.stream().limit(10).map(film -> {
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle(film.getTitle());
			entry.setLink("https://example.com/films/" + film.getFilmId().toString());
			entry.setPublishedDate(Date.from(film.getLastUpdate().toInstant()));

			SyndContent description = new SyndContentImpl();
			description.setType("text/plain");
			description.setValue(film.getDescription());
			entry.setDescription(description);

			return entry;
		}).collect(Collectors.toList());
		feed.setEntries(entries);

		try {
			return new SyndFeedOutput().outputString(feed);
		} catch (FeedException e) {
			throw new InternalServerErrorException(e);
		}
	}
}
