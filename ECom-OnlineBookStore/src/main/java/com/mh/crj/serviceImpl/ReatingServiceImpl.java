package com.mh.crj.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mh.crj.entity.Book;
import com.mh.crj.entity.Rating;
import com.mh.crj.entity.UserRegister;
import com.mh.crj.exception.BookNotFoundException;
import com.mh.crj.exception.CustomerNotFoundException;
import com.mh.crj.model.ReatingDto;
import com.mh.crj.repository.BookRepository;
import com.mh.crj.repository.ReatingRepo;
import com.mh.crj.repository.UserRegisterRepo;
import com.mh.crj.service.ReatingService;

@Service
public class ReatingServiceImpl implements ReatingService{

	@Autowired
	private ReatingRepo reatingRepo;

	@Autowired
	private UserRegisterRepo registerRepo;
	
	@Autowired
	private BookRepository bookRepository;
	
	
	@Override
	public Rating saveReating(ReatingDto reatingDto) {

		
		UserRegister user = registerRepo.findById(reatingDto.getUserId()).orElseThrow(()-> new CustomerNotFoundException("User is not fount with id : "+reatingDto.getUserId()));
		
		Book book = bookRepository.findById(reatingDto.getBookId()).orElseThrow(()-> new BookNotFoundException("Book is not found with id : "+reatingDto.getBookId()));
		
		Rating byUserAndBook = reatingRepo.findByUserAndBook(user, book);
		
		if(byUserAndBook == null) {
			
			Rating reating = new Rating();
			reating.setRate(reatingDto.getRate());
			reating.setReviewText(reatingDto.getReviewText());
			reating.setBook(book);
			reating.setUser(user);
			
			Rating save = reatingRepo.save(reating);
			return save;
		}
		else {
			byUserAndBook.setRate(reatingDto.getRate());
			byUserAndBook.setReviewText(reatingDto.getReviewText());
			 return reatingRepo.save(byUserAndBook);
		}

	}
		
	
	
	@Override
	public Rating updadteReating(ReatingDto reatingDto) {
		
		UserRegister user = registerRepo.findById(reatingDto.getUserId()).orElseThrow(()-> new CustomerNotFoundException("User is not fount with id : "+reatingDto.getUserId()));

		Book book = bookRepository.findById(reatingDto.getBookId()).orElseThrow(()-> new BookNotFoundException("Book is not found with id : "+reatingDto.getBookId()));
		
		Rating byUserAndBook = reatingRepo.findByUserAndBook(user,book);
		
		byUserAndBook.setRate(reatingDto.getRate());
		byUserAndBook.setReviewText(reatingDto.getReviewText());
		
		return reatingRepo.save(byUserAndBook);
		
	}
	
	
	
	@Override
	public List<Rating> fetchAllReating() {

		return reatingRepo.findAll();
	
	}
	
	
	@Override
	public String deleteReating(Long userId, Long bookId) {

		UserRegister user = registerRepo.findById(userId).orElseThrow(()-> new CustomerNotFoundException("User is not fount with id : "+userId));

		Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException("Book is not found with id : "+bookId));
		
		reatingRepo.deleteByUserAndBook(user,book);
		
		return "Reating delete from user "+user.getFirstName();
	}
	
	
}
