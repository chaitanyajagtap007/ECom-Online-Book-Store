package com.mh.crj.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mh.crj.entity.Book;
import com.mh.crj.entity.mongo.BookMongo;
import com.mh.crj.exception.BookNotFoundException;
import com.mh.crj.exception.InternalServerException;
import com.mh.crj.model.BookDto;
import com.mh.crj.repository.BookRepository;
import com.mh.crj.repository.mongo.BookMongoRepo;
import com.mh.crj.service.BookService;

@Service
public class BookServiceImpl implements BookService {


	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookMongoRepo bookMongoRepo;
	
	
	
	@Override
	public Book insertBook(BookDto bookDto) {
		
		Book book= new Book();
		try {
			book.setTitle(bookDto.getTitle());
			book.setAuthor(bookDto.getAuthor());
			book.setPrice(bookDto.getPrice());
			book.setStock(bookDto.getStock());
			bookRepository.save(book);
		
			BookMongo bookMongo = new BookMongo();
			bookMongo.setTitle(bookDto.getTitle());
			bookMongo.setAuthor(bookDto.getAuthor());
			bookMongo.setPrice(bookDto.getPrice());
			bookMongo.setStock(bookDto.getStock());
			bookMongoRepo.save(bookMongo);
			
			
		}catch (InternalServerException e) {
			throw new InternalServerException("Failed to insert book "+e.getMessage());
		}
		
		return book;		
	}

	@Override
	public Book getById(Long id) {

		Optional<Book> byId = bookRepository.findById(id);
		if(byId.isEmpty()) {
			throw new BookNotFoundException("Book is not found with id "+id);
		}else {
			return byId.get();
		}
	}

	@Override
	public List<Book> getAllBooks() {
		
		List<Book> allBooks = bookRepository.findAll();
		if(allBooks.isEmpty()) {
			throw new BookNotFoundException("Books is not available in DB");
		}else {
			return allBooks;
		}
	}

	@Override
	public String deleteBookById(Long id) {
		Optional<Book> byId = bookRepository.findById(id);
		if(byId.isPresent()) {
			bookRepository.deleteById(id);
			return " Book id :"+id+" Data is deleted from DB";
		}else {
			throw new BookNotFoundException("Book is not found with id "+id);
		}
	}

}
