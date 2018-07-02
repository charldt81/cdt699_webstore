
// This 'CartController.java' class was added from Chapter_9

package com.packt.webstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



// Our regular Spring MVC Controller named 'CartController', has two request-mapping methods, namely 'get()' and 'getCart()'.
// Whenever a normal web request comes to the URL 'http://localhost:8080/webstore/cart', the 'get()' method will be invoked, and inside the 
// 'get()' method we have retrieved the session ID and used it as a cart ID to invoke the 'getCart()' method
@Controller
@RequestMapping(value = "/cart")
public class CartController {
	
	
	
	@RequestMapping
	public String get(HttpServletRequest request) {
		return "redirect:/cart/" + request.getSession(true).getId();
	}
	
	
	
	// And within the 'getCart()' method, we simply stored the cartId in the Spring MVC Model and returned a View name as 'cart'.
	// Since we have returned a View name as 'cart', our dispatcher servlet would definitely look for a View file called 'cart.jsp'.
	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
	public String getCart(@PathVariable(value = "cartId") String cartId, Model model) {
		model.addAttribute("cartId",cartId);
		return "cart";
	}

}
