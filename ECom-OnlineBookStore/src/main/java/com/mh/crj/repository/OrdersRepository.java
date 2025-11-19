package com.mh.crj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mh.crj.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long>{

	@Query(value = "SELECT * FROM orders o WHERE o.user_id = :userId AND o.created_date > CURDATE() - INTERVAL 7 DAY" , nativeQuery = true)
	public List<Orders> findAnyLastweekPlaced(Long userId);

}
