package com.example;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.dto.CustomerDTO;

@Path("customers")
public class CustomerResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerDTO> getCustomers() {
		CustomerDTO c = new CustomerDTO();
		c.setFirstName("Joen");
		c.setLastName("Doe");
		CustomerDTO[] items = { c };
		return Arrays.asList(items);
	}
}
