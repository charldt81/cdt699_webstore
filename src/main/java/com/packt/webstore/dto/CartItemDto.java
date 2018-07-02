
// this 'CartItemDto.java' class was added from Chapter_9 - 'Dto' stands for 'Data Transfer Object'

package com.packt.webstore.dto;

import java.io.Serializable;


// Here we have created a data transfer objects called 'CartItemDto' to carry data between the REST client and our backend services.
public class CartItemDto implements Serializable {

	
	private static final long serialVersionUID = -3551573319376880896L;
	
	
	private String id;
	private String productId;
	private int quantity;
	
	
	
	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getProductId() {
		return productId;
	}
	
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
	public int getQuantity() {
		return quantity;
	}
	
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}








