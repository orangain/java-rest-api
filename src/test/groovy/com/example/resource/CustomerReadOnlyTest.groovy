package com.example.resource;

import org.junit.BeforeClass
import org.junit.Test;

import com.example.ApiTestBase

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

		def items = this.parseJsonResponse(response)
		assert items instanceof ArrayList
		assert items.size() == 599

		def item = items[0]
		def rentals = item.remove("rentals")
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

		assert rentals.size() == 32
		def rental = rentals[0]
		assert rental == [
			rentalId:76,
			rentalDate:"2005-05-25T20:30:37",
			inventoryId: 3021,
			customerId:1,
			staffId:2,
			returnDate:"2005-06-03T21:00:37",
			lastUpdate:"2006-02-16T06:30:53",
		]
	}

	@Test
	public void testGetCustomer() {
		def response = target("customers/1").request().get();
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		def rentals = item.remove("rentals")
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
		assert rentals.size() == 32
	}

	@Test
	public void testGetCustomerNotFound() {
		def response = target("customers/1000").request().get();
		assert response.getStatus() == 404
	}
}
