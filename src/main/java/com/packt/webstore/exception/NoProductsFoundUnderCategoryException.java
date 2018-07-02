
// This class was added from Chapter_5

package com.packt.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// The '@ResponseStatus' annotation instructs the Spring MVC to return a specific HTTP status if this exception has been thrown from a request mapping method.
// We can configure which HTTP status needs to be returned via the value attribute of the '@ResponseStatus' annotation;
// in our case we configured HttpStatus.NOT_FOUND, which displays the familiar HTTP 404 response.
// The second attribute, reason, denotes the reason for the HTTP response error.
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No products found under this category")
public class NoProductsFoundUnderCategoryException extends RuntimeException {
	

	private static final long serialVersionUID = 3935230281455340039L;
	
	
	
}
