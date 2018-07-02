
// added this 'ProductIdValidator.java' class from Chapter_8

package com.packt.webstore.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;



// The @Component annotation is another stereotype annotation that is available in Spring.
// It is very similar to the @Repository or @Service annotations; during the booting of our application, Spring would create and maintain 
// an object for the 'ProductIdValidator' class.
// So 'ProductIdValidator' will become a managed bean in our web application context, which is the reason we were able to autowire the
// 'productService' bean in 'ProductIdValidator.java' class.
@Component
public class ProductIdValidator implements ConstraintValidator<ProductId, String> {
	

	// We autowired the 'ProductService' object in the ProductIdValidator class.
	// Inside the 'isValid' method of the ProductIdValidator class, we have used the productService to check whether any product with the given ID exists
	@Autowired
	private ProductService productService;
	
	
	
	public void initialize(ProductId constraintAnnotation) {
		// intentionally left blank; this is the place to initialize the constraint annotation for any sensible default values.
		}
	
	
	
	// If any product exists with the given product ID, we are invalidating the validation by returning false, 
	// otherwise we are passing the validation by returning true.
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Product product;
		
		try {
			product = productService.getProductById(value);
		} catch (ProductNotFoundException e) {
			return true;
		}

		if (product != null) {
			return false;
		}

		return true;
	}
	
	
}
