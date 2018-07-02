
// this 'CartService.java' interface was added from Chapter_9

package com.packt.webstore.service;

import com.packt.webstore.domain.Cart;
import com.packt.webstore.dto.CartDto;



// The 'CartService' interface has the same methods of the 'CartRepository' interface.
public interface CartService {

	
	void create(CartDto cartDto);
	
	Cart read(String cartId);
	
	void update(String cartId, CartDto cartDto);
	
	void delete(String id);
	
	
	
	void addItem(String cartId, String productId);
	
	void removeItem(String cartId, String productId);
	
	
	
	// Added the following two methods from Chapter_10
	Cart validate(String cartId);
	
	void clearCart(String cartId);
	
	
}
