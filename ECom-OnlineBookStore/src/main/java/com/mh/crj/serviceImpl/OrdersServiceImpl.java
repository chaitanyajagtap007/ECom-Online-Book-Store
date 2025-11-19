package com.mh.crj.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mh.crj.entity.Book;
import com.mh.crj.entity.Orders;
import com.mh.crj.entity.UserRegister;
import com.mh.crj.model.OrdersDto;
import com.mh.crj.repository.BookRepository;
import com.mh.crj.repository.OrdersRepository;
import com.mh.crj.repository.UserRegisterRepo;
import com.mh.crj.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService{

	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private UserRegisterRepo registerRepo;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public String saveOrder(OrdersDto orderDto) {

		// 1. check if the request body or titles are empty
		if(orderDto == null || orderDto.getTitle() == null || orderDto.getTitle().isEmpty())
		{
			return "No books  selected. Please select at lest one book to placed";
		}

		
		
		
		// 2. Extract customer ID and selected book titles
		Long userId = orderDto.getUserId();
		Optional<UserRegister> byId = registerRepo.findById(userId);
		
		// check the user id user is available or not in db..
		if(byId.isEmpty()){
			return "User is not available with id :"+userId;
		}
		
		List<String> selectedBooks = orderDto.getTitle();
		UserRegister userRegister = byId.get();
		
		
		// 3. Check whether the user is a Prime member or not
		Boolean isPrime = userRegister.getPrime();
		

		// 4. Apply rules for Non-Prime users
		if(!isPrime) {
			
			// Non-prime users cannot order more than one book at a time
			if(selectedBooks.size()>1) {
				return "Non-prime users can select only one book.";
			}

			// Non-prime users can place only one order per week
			List<Orders> anyLastweekPlaced = ordersRepository.findAnyLastweekPlaced(userId);
			if(!anyLastweekPlaced.isEmpty()) {
				return "Non-prime users can place only one order per week.";
			}
			
		}
		

		// 5. Iterate through each selected book and validate availability
		for (String title : selectedBooks) {

			// Find book details by title
			Book book = bookRepository.findByTitle(title);

			// If the book doesn't exist, return a message
			if(book == null) {
				return "No book found : "+title;
			}
			

			// Create a new order entry for the user
			Orders orders = new Orders();
			orders.setBookId(book.getId());
			orders.setUserId(userId);
			orders.setStstus(false);  // Default status = pending/unprocessed
			

			// Save order into database

			ordersRepository.save(orders);
			
		}
		
		// 6. Return final success message
		return "Order Placed successfully. Thank you.!";
		
	}

//	// Helper method to check whether the user is a Prime user
//	private Boolean checkPrimeUser(Long userId) {
//
//		//fetch user by id
//		Optional<UserRegister> user = registerRepo.findById(userId);
//
//		// Return Prime status if available, else default to false
//		return user.map(UserRegister::getPrime).orElse(false);
//
//	}

}