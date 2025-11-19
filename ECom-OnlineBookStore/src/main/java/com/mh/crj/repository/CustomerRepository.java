package com.mh.crj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mh.crj.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	
}
