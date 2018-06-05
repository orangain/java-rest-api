package com.example.resource;

import org.junit.Before
import org.junit.Test;

import com.example.ApiTestBase

import groovy.json.*;

public class FilmReadWriteTest extends ApiTestBase {

	@Before
	public void setup() {
		ApiTestBase.resetDB();
	}

	@Test
	public void testCreateFilm() {
		def film = [
			title: "Awesome Film",
			description: "This is awesome",
			releaseYear: 2018,
			languageId: 2,
			originalLanguageId: 1,
			rentalDuration: 4,
			rentalRate: 0.98,
			length: 120,
			replacementCost: 10.99,
			rating: "G",
			specialFeatures:"Trailers,Deleted Scenes",
			actors: [
				[
					actor: [actorId: 1]
				],
				[
					actor: [actorId: 10]
				]
			]
		]

		def response = target("films").request().post(this.buildJsonEntity(film));
		assert response.getStatus() == 201
		assert response.getHeaderString("Content-Type") == "application/json"
		assert response.getHeaderString("Location") == "http://localhost:9998/films/1001"

		def item = this.parseJsonResponse(response)
		def lastUpdate = item.remove("lastUpdate")
		def actors = item.remove("actors")

		assert item == [
			filmId: 1001,
			title: "Awesome Film",
			description: "This is awesome",
			releaseYear: 2018,
			languageId: 2,
			originalLanguageId: 1,
			rentalDuration: 4,
			rentalRate: 0.98,
			length: 120,
			replacementCost: 10.99,
			rating: "G",
			specialFeatures:"Trailers,Deleted Scenes",
		]

		assert actors == [
			[
				filmId: 1001,
				lastUpdate: lastUpdate,
				actor: [
					actorId: 1,
					firstName: "PENELOPE",
					lastName: "GUINESS",
					lastUpdate: "2006-02-15T04:34:33Z",
				]
			],
			[
				filmId: 1001,
				lastUpdate: lastUpdate,
				actor: [
					actorId: 10,
					firstName: "CHRISTIAN",
					lastName: "GABLE",
					lastUpdate: "2006-02-15T04:34:33Z",
				]
			]
		]
	}

	@Test
	public void testCreateFilmWithInsufficientFields() {
		def film = [
			title: ""
		]

		def response = target("films").request().post(this.buildJsonEntity(film));
		assert response.getStatus() == 400
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		assert item["message"] == "Validation Error"
		assert item["details"].size() == 4
	}

	@Test
	public void testUpdateFilm() {
		def changes = [
			title: "AWESOME ACADEMY DINOSAUR",
		]
		def response = target("films/1").request().method("PATCH", this.buildJsonEntity(changes));
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		def lastUpdate = item.remove("lastUpdate")
		def filmActors = item.remove("actors")
		assert item == [
			filmId: 1,
			title: "AWESOME ACADEMY DINOSAUR",
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
		]
		assert filmActors.size() == 10
	}

	@Test
	public void testUpdateFilmMoreFields() {
		def changes = [
			title: "AWESOME ACADEMY DINOSAUR",
			releaseYear: 2015,
			rentalRate: 0.89,
			rentalDuration: 14,
			specialFeatures: "",
		]
		def response = target("films/1").request().method("PATCH", this.buildJsonEntity(changes));
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		def lastUpdate = item.remove("lastUpdate")
		def filmActors = item.remove("actors")
		assert item == [
			filmId: 1,
			title: "AWESOME ACADEMY DINOSAUR",
			description: "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies",
			releaseYear: 2015,
			languageId: 1,
			originalLanguageId: null,
			rentalDuration: 14,
			rentalRate: 0.89,
			length: 86,
			replacementCost: 20.99,
			rating: "PG",
			specialFeatures: "",
		]
		assert filmActors.size() == 10
	}

	@Test
	public void testUpdateFilmWithNullValue() {
		def changes = [
			releaseYear: null,
		]
		def response = target("films/1").request().method("PATCH", this.buildJsonEntity(changes));
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		def lastUpdate = item.remove("lastUpdate")
		def filmActors = item.remove("actors")
		assert item == [
			filmId: 1,
			title: "ACADEMY DINOSAUR",
			description: "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies",
			releaseYear: null,
			languageId: 1,
			originalLanguageId: null,
			rentalDuration: 6,
			rentalRate: 0.99,
			length: 86,
			replacementCost: 20.99,
			rating: "PG",
			specialFeatures: "Deleted Scenes,Behind the Scenes",
		]
		assert filmActors.size() == 10
	}

	@Test
	public void testUpdateFilmActors() {
		def changes = [
			actors: [
				[
					actor: [actorId: 1]
				],
				[
					actor: [actorId: 10]
				]
			]
		]
		def response = target("films/1").request().method("PATCH", this.buildJsonEntity(changes));
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		def lastUpdate = item.remove("lastUpdate")
		def filmActors = item.remove("actors").collect {
			it.remove("lastUpdate")
			return it
		}
		assert filmActors == [
			[
				filmId: 1,
				actor: [
					actorId: 1,
					firstName: "PENELOPE",
					lastName: "GUINESS",
					lastUpdate: "2006-02-15T04:34:33Z",
				]
			],
			[
				filmId: 1,
				actor: [
					actorId: 10,
					firstName: "CHRISTIAN",
					lastName: "GABLE",
					lastUpdate: "2006-02-15T04:34:33Z",
				]
			]
		]
	}

	@Test
	public void testUpdateFilmWithDateTimeValue() {
		def changes = [
			lastUpdate: "2018-06-03T11:47:32.000Z",
		]
		def response = target("films/1").request().method("PATCH", this.buildJsonEntity(changes));
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
			lastUpdate: "2018-06-03T11:47:32Z",
		]
		assert filmActors.size() == 10
	}

	@Test
	public void testUpdateFilmWithEmptyObject() {
		def changes = []
		def response = target("films/1").request().method("PATCH", this.buildJsonEntity(changes));
		assert response.getStatus() == 400
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		assert item == [
			message: "Validation Error",
			details: [
				[
					message: "At least one field must be provided",
					path: ["present"],  // This path is confusing
				]
			]
		]
	}

	@Test
	public void testDeleteFilm() {
		def film = [
			title: "Awesome Film",
			description: "This is awesome",
			releaseYear: 2018,
			languageId: 2,
			originalLanguageId: 1,
			rentalDuration: 4,
			rentalRate: 0.98,
			length: 120,
			replacementCost: 10.99,
			rating: "G",
			specialFeatures:"Trailers,Deleted Scenes",
			actors: [],
		]
		assert target("films").request().post(this.buildJsonEntity(film)).getStatus() == 201;

		// Check that item exists before deleting
		assert target("films/1001").request().get().getStatus() == 200

		def response = target("films/1001").request().delete();
		assert response.getStatus() == 204

		// Check that item does not exists after deleting
		assert target("films/1001").request().get().getStatus() == 404
	}
}
