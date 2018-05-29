package com.example;

import org.junit.BeforeClass
import org.junit.Test;

import groovy.json.*;

public class CustomerReadOnlyTest extends ApiTestBase {

	@BeforeClass
	public static void setupClass() {
		ApiTestBase.resetDB();
	}

	@Test
	public void testGetCustomers() {
		def response = target("customers").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def items = new JsonSlurper().parseText(response.readEntity(String.class))
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

	@Test
	public void testGetCustomer() {
		def response = target("customers/1").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = new JsonSlurper().parseText(response.readEntity(String.class))
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
