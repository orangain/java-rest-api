package com.example.sqlmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.dto.Customer;

public interface CustomerMapper {
	Customer selectCustomer(@Param("customerId") int customerId);

	List<Customer> selectCustomer();

	List<Customer> selectCustomer(Customer filter);

	int insertCustomer(Customer customer);

	int updateCustomer(Customer changes);

	int deleteCustomer(@Param("customerId") int customerId);
}
