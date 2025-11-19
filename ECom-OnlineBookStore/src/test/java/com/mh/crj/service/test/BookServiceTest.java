package com.mh.crj.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mh.crj.entity.Book;
import com.mh.crj.entity.Customer;
import com.mh.crj.model.BookDto;
import com.mh.crj.repository.BookRepository;
import com.mh.crj.serviceImpl.BookServiceImpl;

@SpringBootTest
public class BookServiceTest {

	@MockBean
	private BookRepository bookRepository;
	
	@Autowired
	private BookServiceImpl bookServiceImpl;
	
	@Test
	public void insertBookTest() {
		

		// 1. Input data
		BookDto bookRequest = new BookDto();
		bookRequest.setTitle("BookTitle");
		bookRequest.setAuthor("Book Author");
		bookRequest.setPrice(300.0);
		
		// 2. Mock service response
		Book mockResponse = new Book();
		mockResponse.setId(1l);
		mockResponse.setTitle("BookTitle");
		mockResponse.setAuthor("Book Author");
		mockResponse.setPrice(300.0);
		mockResponse.setStock(50);
		
		// 3 when save called then return savedCustomer (fake DB data)
		when(bookRepository.save(any(Book.class))).thenReturn(mockResponse);
		
		// 4 call actual service method
		Book result = bookServiceImpl.insertBook(bookRequest);
		
		// 5 verify the output
		assertNotNull(result);
		assertEquals("BookTitle", result.getTitle());
		assertEquals("Book Author", result.getAuthor());
		// save() call only once
		verify(bookRepository,times(1)).save(any(Book.class));
	}
	
}
