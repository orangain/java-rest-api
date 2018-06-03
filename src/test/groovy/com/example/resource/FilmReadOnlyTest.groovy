package com.example.resource;

import org.junit.BeforeClass
import org.junit.Test;

import com.example.ApiTestBase

import groovy.json.*;

public class FilmReadOnlyTest extends ApiTestBase {

	@BeforeClass
	public static void setupClass() {
		ApiTestBase.resetDB();
	}

	@Test
	public void testGetFilms() {
		def response = target("films").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def items = this.parseJsonResponse(response)
		assert items instanceof ArrayList
		assert items.size() == 1000

		def item = items[0]
		def filmActors = item.remove("actors")
		assert item == [
			filmId: 1,
			title: "ACADEMY DINOSAUR",
			description: "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies",
			releaseYear: 2006,
			languageId: 1,
			originalLanguageId: null,
			rentalDuration: 6,
			rentalRate: 0.99,
			length: 86,
			replacementCost: 20.99,
			rating: "PG",
			specialFeatures: "Deleted Scenes,Behind the Scenes",
			lastUpdate: "2006-02-15T05:03:42Z",
		]

		assert filmActors.size() == 10
		def filmActor = filmActors[0]
		assert filmActor == [
			filmId:1,
			lastUpdate:"2006-02-15T05:05:03Z",
			actor: [
				actorId: 1,
				firstName: "PENELOPE",
				lastName: "GUINESS",
				lastUpdate: "2006-02-15T04:34:33Z",
			]
		]
	}

	@Test
	public void testGetFilm() {
		def response = target("films/1").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		def filmActors = item.remove("actors")
		assert item == [
			filmId: 1,
			title: "ACADEMY DINOSAUR",
			description: "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies",
			releaseYear: 2006,
			languageId: 1,
			originalLanguageId: null,
			rentalDuration: 6,
			rentalRate: 0.99,
			length: 86,
			replacementCost: 20.99,
			rating: "PG",
			specialFeatures: "Deleted Scenes,Behind the Scenes",
			lastUpdate: "2006-02-15T05:03:42Z",
		]

		assert filmActors.size() == 10
	}

	@Test
	public void testGetFilmNotFound() {
		def response = target("films/1001").request().get();
		assert response.getStatus() == 404
	}
}
