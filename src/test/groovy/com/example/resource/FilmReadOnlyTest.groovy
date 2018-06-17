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
			language: [
				languageId: 1,
				name: "English",
				lastUpdate: "2006-02-15T05:02:19Z",
			],
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
	public void testGetFilmsFilterBySimpleField() {
		def response = target("films").queryParam("rentalDuration", "5").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def items = this.parseJsonResponse(response)
		assert items instanceof ArrayList
		assert items.size() == 191

		def item = items[0]
		assert item["rentalDuration"] == 5
	}

	@Test
	public void testGetFilmsFilterByObjectField() {
		def response = target("films").queryParam("language", "1").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def items = this.parseJsonResponse(response)
		assert items instanceof ArrayList
		assert items.size() == 1000

		response = target("films").queryParam("language", "2").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		items = this.parseJsonResponse(response)
		assert items instanceof ArrayList
		assert items.size() == 0
	}

	@Test
	public void testGetFilmsSort() {
		def response = target("films").queryParam("_sort", "-filmId").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def items = this.parseJsonResponse(response)
		assert items instanceof ArrayList
		assert items.size() == 1000

		def item = items[0]
		assert item["filmId"] == 1000
	}

	@Test
	public void testGetFilmsLimit() {
		def response = target("films").queryParam("_limit", "50").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def items = this.parseJsonResponse(response)
		assert items instanceof ArrayList
		assert items.size() == 50
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
			language: [
				languageId: 1,
				name: "English",
				lastUpdate: "2006-02-15T05:02:19Z",
			],
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

	@Test
	public void testCORSInSuccessfulResponse() {
		def response = target("films/1").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Access-Control-Allow-Origin") == "*"
	}

	@Test
	public void testCORSInErrorfulResponse() {
		def response = target("films/1001").request().get();
		assert response.getStatus() == 404
		assert response.getHeaderString("Access-Control-Allow-Origin") == "*"
	}

	@Test
	public void testGetFilmFeed() {
		def response = target("films/_feed").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/atom+xml"

		String feedText = response.readEntity(String.class)
		assert feedText.contains('<feed xmlns="http://www.w3.org/2005/Atom" xmlns:dc="http://purl.org/dc/elements/1.1/">')
	}

	@Test
	public void testEcho() {
		def postItem = [
			message: "こんにちは"
		]
		def response = target("films/_echo").request().post(this.buildJsonEntity(postItem));
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		assert item == postItem
	}
}
