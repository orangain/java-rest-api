package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import groovy.json.*;

public class CustomerTest extends JerseyTest {

	@Override
	protected Application configure() {
		try {
			return new ApiApplication();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testCustomers() {
		final Response response = target("customers").request().get();
		assertEquals(200, response.getStatus());

		def slurper = new JsonSlurper()
		def items = slurper.parseText(response.readEntity(String.class))
		assertTrue(items instanceof ArrayList)
		assertEquals(599, items.size())
	}
}
