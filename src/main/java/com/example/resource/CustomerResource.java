package com.example.resource;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.ibatis.session.SqlSession;
import org.glassfish.jersey.server.ResourceConfig;

import com.example.ApiApplication;
import com.example.dto.Customer;

@Path("customers")
public class CustomerResource {
	@Inject
	Application application;

	private SqlSession openSession() {
		ApiApplication application = (ApiApplication) ((ResourceConfig) this.application).getApplication();
		return application.openSession();
	}

	private Customer doGetCustomer(SqlSession session, int customerId) {
		final Customer parameter = new Customer(customerId);
		return session.selectOne("com.example.selectCustomer", parameter);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getCustomers() {
		try (SqlSession session = this.openSession()) {
			return session.selectList("com.example.selectCustomer");
		}
	}

	@GET
	@Path("{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@PathParam("customerId") int customerId) {
		try (SqlSession session = this.openSession()) {
			Customer customer = this.doGetCustomer(session, customerId);
			if (customer == null) {
				return Response.status(Status.NOT_FOUND).build();
			}

			return Response.ok(customer).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCustomer(Customer customer, @Context UriInfo uriInfo) {
		try (SqlSession session = this.openSession()) {
			int numAffected = session.insert("com.example.insertCustomer", customer);
			if (numAffected == 0) {
				return Response.serverError().entity("Failed to insert").build();
			}
			session.commit();
		}

		int createdCustomerId = customer.getCustomerId();
		// Ensure to return committed result
		try (SqlSession session = this.openSession()) {
			Customer createdCustomer = this.doGetCustomer(session, createdCustomerId);
			if (createdCustomer == null) {
				return Response.serverError().entity("Something wrong").build();
			}

			// See: https://stackoverflow.com/a/26094619
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			URI location = builder.path(Integer.toString(createdCustomerId)).build();

			return Response.created(location).entity(createdCustomer).build();
		}
	}

	@PATCH
	@Path("{customerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer, @PathParam("customerId") int customerId) {
		try (SqlSession session = this.openSession()) {
			customer.setCustomerId(customerId);
			int numAffected = session.update("com.example.updateCustomer", customer);
			if (numAffected == 0) {
				return Response.serverError().entity("Failed to update").build();
			}

			session.commit();
		}

		// Ensure to return committed result
		try (SqlSession session = this.openSession()) {
			Customer updatedCustomer = this.doGetCustomer(session, customerId);
			if (updatedCustomer == null) {
				return Response.serverError().entity("Something wrong").build();
			}

			return Response.ok(updatedCustomer).build();
		}
	}

	@DELETE
	@Path("{customerId}")
	public Response deleteCustomer(@PathParam("customerId") int customerId) {
		try (SqlSession session = this.openSession()) {
			final Customer parameter = new Customer(customerId);
			int numAffected = session.delete("com.example.deleteCustomer", parameter);
			if (numAffected == 0) {
				return Response.status(Status.NOT_FOUND).build();
			}

			session.commit();
			return Response.noContent().build();
		}
	}
}
