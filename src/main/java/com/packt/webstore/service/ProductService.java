package com.packt.webstore.service;

import java.util.List;
import java.util.Map;

import com.packt.webstore.domain.Product;



public interface ProductService {

	
	List <Product> getAllProducts();
	
	List<Product> getProductsByCategory(String category);							// added from Chapter_3
	
	List<Product> getProductsByFilter(Map<String, List<String>> filterParams);		// added from Chapter_3
	
	Product getProductById(String productID);										// added from Chapter_3
	
	void addProduct(Product product);												// added from Chapter_4
	
	void updateAllStock();
	

}
