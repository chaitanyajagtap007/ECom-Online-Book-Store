package com.mh.crj.service;

import java.util.List;

import com.mh.crj.entity.Customer;

public interface CustomerService{

	public Customer insertCustomer(Customer customer);

	public Customer updateCustomer(Customer customer);
	
	public Customer saveOrUpdateCustomer(Customer customer);
	
	public Customer getById(Long id);
	
	public List<Customer> getAllCustomers();
}
