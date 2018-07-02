
// added this interface from Chapter_8

package com.packt.webstore.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


// Every custom validation annotation we create needs to be annotated with the @Constraint (javax.validation.Constraint) annotation.
// The @Constraint annotation has an important property called 'validatedBy', which indicates the class that is performing the actual validation.
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ProductIdValidator.class)
@Documented
public @interface ProductId {

	
	String message() default "{com.packt.webstore.validator.ProductId.message}";
	
	Class<?>[] groups() default {};
	
	public abstract Class<? extends Payload>[] payload() default {};
	
}
