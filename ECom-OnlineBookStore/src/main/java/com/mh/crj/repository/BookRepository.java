package com.mh.crj.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mh.crj.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	public Book findByTitle(String title);

}
