
// this 'CartRestController.java' class was added from Chapter_9

package com.packt.webstore.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.packt.webstore.domain.Cart;
import com.packt.webstore.dto.CartDto;
import com.packt.webstore.service.CartService;



// The 'CartRestController' class mainly has six methods to handle web requests for CRUD operations on Cart objects, namely create, read, update, and delete,
// and two more methods, 'addItem()' and 'removeItem()', to handle adding and removing a 'CartItem' from the 'Cart' object.
// The 'CartRestController' class is just like any other normal Spring MVC Controller, because it has the same @RequestMapping annotations.
// What makes it special enough to become a REST-style Controller is the @RestController and @RequestBody annotations.
// The @RestController annotation instructs Spring to convert all Java objects that are returned from the request-mapping methods 
// into JSON/XML format and send a response in the body of the HTTP response.
@RestController
@RequestMapping(value = "rest/cart")
public class CartRestController {

	
	@Autowired
	private CartService cartService;
	
	
	
	// When you send an HTTP request to a Controller method with JSON/XML data in it, the @RequestBody annotation instructs 
	// Spring to convert it into the corresponding Java object.
	// That's why the 'create()' method has a 'cartDto' parameter annotated with a @RequestBody annotation.
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void create(@RequestBody CartDto cartDto) {
		cartService.create(cartDto);
	}
	
	
	
	// Usually every Controller method is used to return a View name, so that the dispatcher servlet can find the appropriate View file and 
	// dispatch that View file to the client, but here we have returned the object (Cart) itself.
	// Instead of putting the object into the Model, we have returned the object itself.
	// We have done this because we want to return the state of the object in JSON format.
	// Remember, REST-based web services should return data in JSON or XML format and the client can use the data however they want; 
	// they may render it to an HTML page, or they may send it to some external system as it is, as raw JSON/XML data.
	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET) 
	public Cart read(@PathVariable(value = "cartId") String cartId) {
		return cartService.read(cartId);
	}
	
	
	
	@RequestMapping(value = "/{cartId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void update(@PathVariable(value = "cartId") String cartId, @RequestBody CartDto cartDto) {
		cartDto.setId(cartId);
		cartService.update(cartId, cartDto);
	}
	
	
	
	@RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable(value = "cartId") String cartId) {
		cartService.delete(cartId);
	}
	
	
	
	// We have two more requestMapping methods that take care of adding and removing a 'CartItem' from the 'Cart' object, 'addItem()' and 'removeItem()'
	// These methods are considered update methods, which is why both the 'addItem()' and 'removeItem()' methods have PUT as a request method type 
	// in their @RequestMapping annotation.
	// If you send a PUT request to the URL 'http://localhost:8080/webstore/rest/cart/add/P1236', a product with a product id P1236 will 
	// be added to the Cart object.
	@RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void addItem(@PathVariable String productId, HttpSession session) {
		cartService.addItem(session.getId(),productId);
	}
	
	
	
	// If you send a PUT request to the URL 'http://localhost:8080/webstore/rest/cart/remove/P1236', a product with P1236 will be removed from the Cart object.
	@RequestMapping(value = "/remove/{productId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeItem(@PathVariable String productId, HttpSession session) {
		cartService.removeItem(session.getId(),productId);
	}
	
	
	
}

















