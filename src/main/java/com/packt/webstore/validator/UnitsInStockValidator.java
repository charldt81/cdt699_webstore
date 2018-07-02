
// this UnitsInStockValidator.java class was added from Chapter_8



package com.packt.webstore.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.packt.webstore.domain.Product;



// In classic Spring validation, the main validation construct is the Validator (org.springframework.validation.Validator) interface.
// The Spring Validator interface defines two methods for validation purposes, namely supports and validate.
// Every Spring- based validator we are creating should implement this interface.
// We created a class called 'UnitsInStockValidator', which implements the Spring Validator interface.
@Component
public class UnitsInStockValidator implements Validator{

	
	
	// The 'supports()' method indicates whether the validator can validate a specific class.
	// If so, the 'validate()' method can be called to validate an object of that class.
	public boolean supports(Class<?> clazz) {
		return Product.class.isAssignableFrom(clazz);
	}
	
	
	
	// Here simply check whether the given Product object has a unit price greater than 1,000 and the number of units in stock is more than 99,
	// if so, we reject that value with the corresponding error key to show the error message from the message source file.
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		
		if(product.getUnitPrice()!= null && new BigDecimal(1000).compareTo(product.getUnitPrice())<=0 && product.getUnitsInStock()>99) {
			errors.rejectValue("unitsInStock", "com.packt.webstore.validator.UnitsInStockValidator.message");
		}
	}
	
	
	
	
	
}
