
// this 'CartServiceImpl.java' class was added from Chapter_9

package com.packt.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Cart;
import com.packt.webstore.domain.repository.CartRepository;
import com.packt.webstore.dto.CartDto;
import com.packt.webstore.exception.InvalidCartException;
import com.packt.webstore.service.CartService;



// The 'CartServiceImpl' class just internally uses 'InMemoryCartRepository' to carry out all the CRUD operations.
@Service
public class CartServiceImpl implements CartService {
	
	
	@Autowired
	private CartRepository cartRepository;
	
	
	public void create(CartDto cartDto) {
		cartRepository.create(cartDto);
	}
	
	
	
	@Override
	public Cart read(String id) {
		return cartRepository.read(id);
	}
	
	
	
	@Override
	public void update(String id, CartDto cartDto) {
		cartRepository.update(id, cartDto);
	}
	
	
	
	@Override
	public void delete(String id) {
		cartRepository.delete(id);
	}
	
	
	
	@Override
	public void addItem(String cartId, String productId) {
		cartRepository.addItem(cartId, productId);
	}
	
	
	
	@Override
	public void removeItem(String cartId, String productId) {
		cartRepository.removeItem(cartId, productId);
	}
	
	
	
	// Added from Chapter_10
	@Override
	public Cart validate(String cartId) {
		Cart cart = cartRepository.read(cartId);
		if(cart == null || cart.getCartItems().size() == 0) {
			throw new InvalidCartException(cartId);
		}
		return cart;
	}
	
	
	
	// Added from Chapter_10
	@Override
	public void clearCart(String cartId) {
		cartRepository.clearCart(cartId);
	}
	
	
	

}









