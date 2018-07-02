
// this 'CartRepository.java' interface was added from Chapter_9

package com.packt.webstore.domain.repository;

import com.packt.webstore.domain.Cart;
import com.packt.webstore.dto.CartDto;



// Here we have defined six methods to take care of CRUD operations (Create, Read, Update, and Delete) on the CART_ITEM table.
public interface CartRepository {

	
	void create(CartDto cartDto);
	
	Cart read(String id);
	
	void update(String id, CartDto cartDto);
	
	void delete(String id);
	
	
	
	void addItem(String cartId, String productId);
	
	void removeItem(String cartId, String productId);
	
	
	
	// Added the line below from Chapter_10
	void clearCart(String cartId);
	
}
