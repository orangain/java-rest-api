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

		def response = target("customers").request().post(Entity.json(customerJson));
		assert response.getStatus() == 201
		assert response.getHeaderString("Location") == "http://localhost:9998/customers/600"

		def item = new JsonSlurper().parseText(response.readEntity(String.class))
		def createDate = item.remove("createDate")
		def lastUpdate = item.remove("lastUpdate")

		assert item == [
			customerId: 600,
			storeId: 1,
			firstName: "JANE",
			lastName: "DOE",
			email: "JANE.DOE@sakilacustomer.org",
			address: [
				addressId: 1,
				address: "47 MySakila Drive",
				address2: null,
				district: "Alberta",
				lastUpdate: "2014-09-26T07:30:27",
			],
			active: false,
		]

		assert createDate == lastUpdate
	}
}
