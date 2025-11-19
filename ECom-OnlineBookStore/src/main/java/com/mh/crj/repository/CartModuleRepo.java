package com.mh.crj.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mh.crj.entity.Book;
import com.mh.crj.entity.CartModule;
import com.mh.crj.entity.Customer;

public interface CartModuleRepo extends JpaRepository<CartModule, Long>{

	public CartModule findByCustomerAndBook(Customer customer, Book book);
	public List<CartModule> findByCustomerId(Long id);
	
}
