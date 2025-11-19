package com.mh.crj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mh.crj.entity.UserRegister;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRegisterRepo extends JpaRepository<UserRegister, Long> {

	public Optional<UserRegister> findByEmail(String email);
	
}
