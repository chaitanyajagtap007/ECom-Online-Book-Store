package com.mh.crj.service;

import java.util.List;

import com.mh.crj.entity.Book;
import com.mh.crj.model.BookDto;

public interface BookService {

	public Book insertBook(BookDto bootDto);
	public Book getById(Long id);
	public List<Book> getAllBooks();
	public String deleteBookById(Long id);
}
