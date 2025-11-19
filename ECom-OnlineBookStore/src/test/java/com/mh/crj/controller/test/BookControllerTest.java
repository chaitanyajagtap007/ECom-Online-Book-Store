package com.mh.crj.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mh.crj.controller.BookController;
import com.mh.crj.entity.Book;
import com.mh.crj.model.BookDto;
import com.mh.crj.service.BookService;

@WebMvcTest(BookController.class)
public class BookControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookService bookService;
	
	@Test
	public void testInsertBook() throws Exception{
		
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
		
		// 3. mock service call
		when(bookService.insertBook(bookRequest)).thenReturn(mockResponse);
		
		
		// 4. perform post and validate response
		mockMvc.perform(post("/saveBook").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(bookRequest)))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		
	}
	
}
