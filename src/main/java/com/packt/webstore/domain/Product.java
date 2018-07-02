package com.packt.webstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.packt.webstore.validator.ProductId;


// this Product{} class is used as the 'domain object' that holds the details of a product, such as the name, description, price, etc... step 1


// This annotation '@XmlRootElement' was added from Chapter_5
@XmlRootElement
public class Product implements Serializable {

	
	private static final long serialVersionUID = 3678107792576131001L;
	
	// Using these annotations, we can define validation constraints on fields.
	// There are more validation constraint annotations available under the javax.validation.constraints package and can be found at the following address:
	//    https://docs.oracle.com/javaee/7/tutorial/bean-validation001.htm
	// This @Pattern annotation field will check whether the given value for the field matches the regular expression that is specified in the 'regexp' attribute.
	// Here it is specified that it should start with 'P' and should be followed by digits '[1-9]'
	// The message attribute of every validation annotation is just acting as a key to the actual message from the message source file (messages.properties).
	// We specified Pattern.Product.productId.validation as the key, so we need to define the actual validation message in the message source file.
	// Added this @Pattern annotation from Chapter_8 and the 'message="{Pattern.Product.productId.validation}' is pulled from 'messages.properties' file.
	// Here we are using our newly created 'ProductId' validation annotation @ProductId which is the 'ProductId.java' interface we created.
	// So that @ProductID annotation will act similarly to any other JSR-380 Bean Validation annotation.
	@Pattern(regexp="P[1-9]+", message="{Pattern.Product.productId.validation}")
	@ProductId
	private String productId;
	
	// Added this @Size annotation from Chapter_8 and the message attribute is pulled from the 'messages.properties' file
	@Size(min=4, max=50, message="{Size.Product.name.validation}")
	private String name;
	
	// Added these @Min, @Digits and @NotNull annotation from Chapter_8 and the message attribute is pulled from the 'messages.properties' file
	@Min(value=0, message="{Min.Product.unitPrice.validation}")
	@Digits(integer=8, fraction=2, message="{Digits.Product.unitPrice.validation}")
	@NotNull(message= "{NotNull.Product.unitPrice.validation}")
	private BigDecimal unitPrice;
	
	private String description;
	private String manufacturer;
	private String category;
	private long unitsInStock;
	private long unitsInOrder;
	private boolean discontinued;
	private String condition;
	
	// Added from Chapter_5 including getters and setters
	// This 'MultipartFile' reference holds the actual product image file that we are uploading.
	// This '@JsonIgnore' annotation was added from Chapter_5 and is used to NOT represent the product image as part of the JSON View.
	@JsonIgnore
	private MultipartFile productImage;
	
	
	public Product() {
		super();
	}
	
	
	public Product(String productId, String name, BigDecimal unitPrice) {
		this.productId = productId;
		this.name = name;
		this.unitPrice = unitPrice;
	}


	
	// getters and setters...
	
	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getManufacturer() {
		return manufacturer;
	}


	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public long getUnitsInStock() {
		return unitsInStock;
	}


	public void setUnitsInStock(long unitsInStock) {
		this.unitsInStock = unitsInStock;
	}


	public long getUnitsInOrder() {
		return unitsInOrder;
	}


	public void setUnitsInOrder(long unitsInOrder) {
		this.unitsInOrder = unitsInOrder;
	}


	public boolean isDiscontinued() {
		return discontinued;
	}


	public void setDiscontinued(boolean discontinued) {
		this.discontinued = discontinued;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}

	
	// This '@XmlTransient' annotation was added from Chapter_5 and is used to NOT represent the product image as part of the XML View.
	@XmlTransient
	public MultipartFile getProductImage() {
		return productImage;
	}


	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}


	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Product other = (Product) obj;
		
		if (productId == null) {
			if (other.productId != null)
				return false;
			
		} else if (!productId.equals(other.productId))
			return false;
		
		return true;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}
	
	
}
