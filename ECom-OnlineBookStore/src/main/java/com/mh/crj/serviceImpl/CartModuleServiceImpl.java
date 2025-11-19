package com.mh.crj.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mh.crj.entity.Book;
import com.mh.crj.entity.CartModule;
import com.mh.crj.entity.Customer;
import com.mh.crj.exception.BookNotFoundException;
import com.mh.crj.exception.CustomerNotFoundException;
import com.mh.crj.repository.BookRepository;
import com.mh.crj.repository.CartModuleRepo;
import com.mh.crj.repository.CustomerRepository;
import com.mh.crj.service.CartModuleService;

@Service
public class CartModuleServiceImpl implements CartModuleService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired 
	private BookRepository bookRepository;
	
	@Autowired
	private CartModuleRepo cartModuleRepo;
	
	@Override
	public CartModule addToCart(Long customerId, Long bookId, Integer quantity) {

		//check customer is available or not
		Customer customer =	customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer is not found"));
		
		//check book is available or not
		Book book =	bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException("Book is not found"));
		
		
		//check this user already added this book in their cart 
		// it helps to avoid duplicate	
		CartModule cartItem = cartModuleRepo.findByCustomerAndBook(customer,book);
		
		//cart iteams is alread exists, update quantity
		if(cartItem!=null) {
			//add nw quentity and new one
			cartItem.setQuantity(cartItem.getQuantity()+quantity);
		}
		// if cart items not found then create new
		else {
			cartItem = new CartModule(quantity, book, customer);
		}
		
		//calculate total price
		cartItem.setTotalPrice(cartItem.getQuantity() * book.getPrice());
		
		// save is DB and return 
		return cartModuleRepo.save(cartItem);
		 
	}

	@Override
	public void removeFromCart(Long id) {

		cartModuleRepo.deleteById(id);
		
	}

	@Override
	public List<CartModule> retriveCarts() {
		List<CartModule> all = cartModuleRepo.findAll();
		return all;
	}
	
	@Override
	public List<CartModule> retriveCartsByCustomerId(Long id) {
		List<CartModule> byCustomerId = cartModuleRepo.findByCustomerId(id);
		return byCustomerId;
	}
	
}
