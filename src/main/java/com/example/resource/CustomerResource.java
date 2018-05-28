package com.example.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.session.SqlSession;
import org.glassfish.jersey.server.ResourceConfig;

import com.example.ApiApplication;
import com.example.dto.Customer;

@Path("customers")
public class CustomerResource {
	@Inject
	Application application;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getCustomers() {
		ApiApplication application = (ApiApplication) ((ResourceConfig) this.application).getApplication();

		try (SqlSession session = application.openSession()) {
			return session.selectList("com.example.customerSelect");
		}
	}
}
