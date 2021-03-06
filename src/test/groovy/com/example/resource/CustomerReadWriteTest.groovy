package com.example.resource;

import org.junit.Before
import org.junit.Test;

import com.example.ApiTestBase

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
			active: true,
		]

		def response = target("customers").request().post(this.buildJsonEntity(customer));
		assert response.getStatus() == 201
		assert response.getHeaderString("Content-Type") == "application/json"
		assert response.getHeaderString("Location") == "http://localhost:9998/customers/600"

		def item = this.parseJsonResponse(response)
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
				lastUpdate: "2014-09-25T22:30:27Z",
			],
			active: true,
			rentals: [],
		]

		assert createDate == lastUpdate
	}

	@Test
	public void testUpdateCustomer() {
		def changes = [
			firstName: "ALICE",
		]
		def response = target("customers/1").request().method("PATCH", this.buildJsonEntity(changes));
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		def lastUpdate = item.remove("lastUpdate")
		def rentals = item.remove("rentals")
		assert item == [
			customerId: 1,
			storeId: 1,
			firstName: "ALICE",
			lastName: "SMITH",
			email: "MARY.SMITH@sakilacustomer.org",
			address: [
				addressId: 5,
				address: "1913 Hanoi Way",
				address2: "",
				district: "Nagasaki",
				lastUpdate: "2014-09-25T22:31:53Z",
			],
			active: true,
			createDate: "2006-02-14T22:04:36Z",
		]
		assert rentals.size() == 32
	}

	@Test
	public void testUpdateCustomerMoreFields() {
		def changes = [
			storeId: 2,
			firstName: "ALICE",
			address: [
				addressId: 1,
			],
			active: false,
		]
		def response = target("customers/1").request().method("PATCH", this.buildJsonEntity(changes));
		assert response.getStatus() == 200
		assert response.getHeaderString("Content-Type") == "application/json"

		def item = this.parseJsonResponse(response)
		def lastUpdate = item.remove("lastUpdate")
		def rentals = item.remove("rentals")
		assert item == [
			customerId: 1,
			storeId: 2,
			firstName: "ALICE",
			lastName: "SMITH",
			email: "MARY.SMITH@sakilacustomer.org",
			address: [
				addressId: 1,
				address: "47 MySakila Drive",
				address2: null,
				district: "Alberta",
				lastUpdate: "2014-09-25T22:30:27Z",
			],
			active: false,
			createDate: "2006-02-14T22:04:36Z",
		]
		assert rentals.size() == 32
	}

	@Test
	public void testDeleteCustomer() {
		def customer = [
			storeId: 1,
			firstName: "JANE",
			lastName: "DOE",
			email: "JANE.DOE@sakilacustomer.org",
			address: [
				addressId: 1,
			],
			active: true,
		]
		assert target("customers").request().post(this.buildJsonEntity(customer)).getStatus() == 201;

		// Check that item exists before deleting
		assert target("customers/600").request().get().getStatus() == 200

		def response = target("customers/600").request().delete();
		assert response.getStatus() == 204

		// Check that item does not exists after deleting
		assert target("customers/600").request().get().getStatus() == 404
	}
}
