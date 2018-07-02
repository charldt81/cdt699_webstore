
// added this ProductValidator.java class from Chapter_8


package com.packt.webstore.validator;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.packt.webstore.domain.Product;



// This 'ProductValidator' class is nothing but an implementation of the regular Spring validator.
public class ProductValidator implements Validator {

	
	// We have autowired our existing bean validator, in the 'WebApplicationContextConfig.java' class, into the 'ProductValidator' class through the following line:
	@Autowired
	private javax.validation.Validator beanValidator;
	
	
	
	private Set<Validator> springValidators;
	
	
	
	public ProductValidator() {
		springValidators = new HashSet<Validator>();
	}
	
	
	
	public void setSpringValidators(Set<Validator> springValidators) {
		this.springValidators = springValidators;
	}
	
	
	
	public boolean supports(Class<?> clazz) {
		return Product.class.isAssignableFrom(clazz);
	}
	
	
	
	// Here we used the 'beanValidator' reference inside the 'validate()' method of the ProductValidator class to validate all Bean Validation annotations.
	// The 'beanValidator.validate(target)' statement returned all the constraint violations.
	// Then, using the errors object, we threw all the invalid constraints as error messages.
	// So every Bean Validation annotation we specified in the Product domain class will get handled within a for loop.
	public void validate(Object target, Errors errors) {
		Set<ConstraintViolation<Object>> constraintViolations = beanValidator.validate(target);
		
		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			String propertyPath = constraintViolation.getPropertyPath().toString();
			String message = constraintViolation.getMessage();
			errors.rejectValue(propertyPath, "", message);
		}
		
		// Similarly, we have one more for loop to handle all Spring validations in the 'validate()' method of the 'ProductValidator' class.
		// This for loop iterates through the set of Spring validators and validates them one by one.
		// We haven't initiated the 'springValidators' reference, so we have created a bean for the 'ProductValidator' class in our web application context and
		// assigned the 'springValidators' set.
		for(Validator validator: springValidators) {
			validator.validate(target, errors);
		}
		
		// So now we have created a common adapter validator that can adopt Bean Validation and Spring validation,
		// and validates all Spring and Bean-based validations together.
		
	}
	
	

}


















