
// this 'CartDto.java' class was added from Chapter_9 - 'Dto' stands for 'Data Transfer Object'

package com.packt.webstore.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



// Here we have created a data transfer objects called 'CartDto' to carry data between the REST client and our backend services.
public class CartDto implements Serializable {

	
	private static final long serialVersionUID = -2017182726290898588L;
	
	private String id;
	private List<CartItemDto> cartItems;
	
	
	public CartDto() {}
	
	
	public CartDto(String id) {
		this.id = id;
		cartItems = new ArrayList<>();
	}
	
	
	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public List<CartItemDto> getCartItems() {
		return cartItems;
	}
	
	
	public void setCartItems(List<CartItemDto> cartItems) {
		this.cartItems = cartItems;
	}
	
	
	public void addCartItem(CartItemDto cartItemDto) {
		this.cartItems.add(cartItemDto);
	}
	
	
	
}
