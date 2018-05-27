package com.example;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

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
	public void test() {
		final Response response = target("customers").request().get();
		assertEquals(response.getStatus(), 200);
	}
}
