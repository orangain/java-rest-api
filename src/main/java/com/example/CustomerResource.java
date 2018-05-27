package com.example;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.session.SqlSession;
import org.glassfish.jersey.server.ResourceConfig;

import com.example.dto.CustomerDTO;
import com.example.entity.Customer;

@Path("customers")
public class CustomerResource {
	@Inject
	Application application;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerDTO> getCustomers() {
		ApiApplication application = (ApiApplication) ((ResourceConfig) this.application).getApplication();

		try (SqlSession session = application.openSession()) {
			List<Customer> result = session.selectList("com.example.customerSelect");

			result.forEach(customer -> {
				System.out.println(customer);
			});
		}

		CustomerDTO c = new CustomerDTO();
		c.setFirstName("Joen");
		c.setLastName("Doe");
		CustomerDTO[] items = { c };
		return Arrays.asList(items);
	}
}
