package com.mh.crj.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.mh.crj.entity.Book;
import com.mh.crj.entity.Rating;
import com.mh.crj.entity.UserRegister;

import jakarta.transaction.Transactional;




public interface ReatingRepo extends JpaRepository<Rating, Long>{

	public Rating findByUserAndBook(UserRegister user,Book book);

	@Transactional
	public void deleteByUserAndBook(UserRegister user,Book book);

}
