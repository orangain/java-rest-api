package com.example;

import javax.ws.rs.client.Entity;

import org.junit.Before
import org.junit.Test;

import groovy.json.*;

public class CustomerReadWriteTest extends ApiTestBase {

	@Before
	public void setup() {
		ApiTestBase.resetDB();
	}

	@Test
	public void testCreateCustomer() {
		def customer = [
			storeId: 1,
			firstName: "JANE",
			lastName: "DOE",
			email: "JANE.DOE@sakilacustomer.org",
			address: [
				addressId: 1,
			],
		]
		def customerJson = new JsonBuilder(customer).toString();
		System.out.println(customerJson)
		def response = target("customers").request().post(Entity.json(customerJson));
		assert response.getStatus() == 200
	}
}
