package com.example;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response

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
		assert response.getStatus() == 200

		def slurper = new JsonSlurper()
		def items = slurper.parseText(response.readEntity(String.class))
		assert items instanceof ArrayList
		assert items.size() == 599
		def item = items[0]
		assert item == [
			customerId: 1,
			storeId: 1,
			firstName: "MARY",
			lastName: "SMITH",
			email: "MARY.SMITH@sakilacustomer.org",
			address: [
				addressId: 5,
				address: "1913 Hanoi Way",
				address2: "",
				district: "Nagasaki",
				lastUpdate: "2014-09-26T07:31:53",
			],
			active: true,
			createDate: "2006-02-15T07:04:36",
			lastUpdate: "2006-02-15T13:57:20",
		]
	}
}
