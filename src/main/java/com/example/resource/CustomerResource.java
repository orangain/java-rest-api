package com.example.resource;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;
import org.glassfish.jersey.server.ResourceConfig;

import com.example.ApiApplication;
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
			List<Customer> customers = session.selectList("com.example.customerSelect");

			return customers.stream().map(customerEntity -> {
				CustomerDTO customerDTO = new CustomerDTO();
				try {
					BeanUtils.copyProperties(customerDTO, customerEntity);
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
				return customerDTO;
			}).collect(Collectors.toList());
		}
	}
}
