package com.mh.crj.service;

import java.util.List;

import com.mh.crj.entity.CartModule;

public interface CartModuleService {

	public CartModule addToCart(Long customerId, Long bookId, Integer quantity);

	public void removeFromCart(Long id);

	public List<CartModule> retriveCarts();
	
	public List<CartModule> retriveCartsByCustomerId(Long id);

}
