package com.example.resource;

import org.junit.Before
import org.junit.Test;

import com.example.ApiTestBase

import groovy.json.*;

public class MailReadWriteTest extends ApiTestBase {

	@Before
	public void setup() {
		ApiTestBase.resetDB();
	}

	@Test
	public void testCreateMail() {
		def itemToPost = [
			"from": "alice@example.com",
			"to": "bob@example.com",
			"cc": "",
			"subject": "Hello",
			"body": "こんにちは",
			"attachments": [
				[
					"filename": "hello.txt",
					"content": "SGVsbG8sIFdvcmxkIQ0K44GT44KT44Gr44Gh44Gv"
				]
			]
		]

		def response = target("/mails").request().post(this.buildJsonEntity(itemToPost));
		assert response.getStatus() == 201
		assert response.getHeaderString("Content-Type") == "application/json"
		assert response.getHeaderString("Location") == "http://localhost:9998/mails/1"

		def item = this.parseJsonResponse(response)

		assert item == [
			"mailId": 1,
			"from": "alice@example.com",
			"to": "bob@example.com",
			"cc": "",
			"subject": "Hello",
			"body": "こんにちは",
			"isSucceeded": true,
		]

		response = target("/mails/1/download/0/hello.txt").request().get();
		assert response.getStatus() == 200
		def body = response.readEntity(String.class)
		assert body == "Hello, World!\r\nこんにちは"
	}
}
