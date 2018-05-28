package com.example.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.session.SqlSession;
import org.glassfish.jersey.server.ResourceConfig;

import com.example.ApiApplication;
import com.example.dto.Customer;
import com.example.parameter.CustomerParameter;

@Path("customers")
public class CustomerResource {
	@Inject
	Application application;

	private SqlSession openSession() {
		ApiApplication application = (ApiApplication) ((ResourceConfig) this.application).getApplication();
		return application.openSession();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getCustomers() {
		try (SqlSession session = this.openSession()) {
			return session.selectList("com.example.selectCustomer");
		}
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("id") int id) {
		final CustomerParameter parameter = new CustomerParameter();
		parameter.setCustomerId(id);

		try (SqlSession session = this.openSession()) {
			return session.selectOne("com.example.selectCustomer", parameter);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Customer createCustomer(Customer customer) {
		try (SqlSession session = this.openSession()) {
			int numAffected = session.insert("com.example.insertCustomer", customer);
			if (numAffected == 0) {
				throw new RuntimeException("Failed to insert");
			}
			return customer;
		}
	}
}
