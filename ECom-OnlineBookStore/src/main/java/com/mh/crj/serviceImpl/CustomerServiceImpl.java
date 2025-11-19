package com.mh.crj.serviceImpl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mh.crj.entity.Customer;
import com.mh.crj.entity.mongo.CustomerMongo;
import com.mh.crj.exception.CustomerNotFoundException;
import com.mh.crj.repository.CustomerRepository;
import com.mh.crj.repository.mongo.CustomerMongoRepo;
import com.mh.crj.service.CustomerService;

@Service
public class CustomerServiceImpl  implements CustomerService{


	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private CustomerMongoRepo customerMongoRepo;
	
	@Override
	public Customer insertCustomer(Customer customer) {
		
		Customer saveCustomer = customerRepo.save(customer);
		
		CustomerMongo customerMongo = new CustomerMongo();
		customerMongo.setName(customer.getName());
		customerMongo.setEmail(customer.getEmail());
		customerMongo.setPhone(customer.getPhone());
		customerMongo.setCreatedDate(customer.getCreatedDate());
		customerMongo.setUpdatedDate(customer.getUpdatedDate());
		
		customerMongoRepo.save(customerMongo);
		
		return saveCustomer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		Customer updateCustomer = customerRepo.save(customer);
		return updateCustomer;
	}

	@Override
	public Customer saveOrUpdateCustomer(Customer customer) {
		Customer data = customerRepo.save(customer);
		return data;
	}

	@Override
	public Customer getById(Long id) {
		Optional<Customer> byId = customerRepo.findById(id);
		if(!byId.isPresent()) {
			throw new CustomerNotFoundException("Customer not found with id "+id);
//			throw new NullPointerException("Customer not Found");
		}
		return byId.get();
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}
	
	
//	@Override
//	public String saveOrUpdate(Customer customer) {
//        Optional<Customer> existing = customerRepo.findById(customer.getId() == null ? 0L : customer.getId());
//
//        if (existing.isEmpty()) {
//        	customerRepo.save(customer);
//            return "Customer saved with ID: " + customer.getId();
//        } else {
//        	customerRepo.save(customer);
//            return "Customer updated with ID: " + customer.getId();
//        }
//    }
	
		
}
