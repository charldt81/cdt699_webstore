package com.packt.webstore.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;
import com.packt.webstore.validator.ProductValidator;


// to put the product information in a model, we needed to create this controller class ProductController{}... step3

// we added this relative request mapping annotation at the class level... now we access the products at '/market/products'
@RequestMapping("market")
@Controller
public class ProductController {
	
	
	@Autowired
	private ProductService productService;

	
	// we just have a single method called list whose responsibility it is to create a product domain object to hold the information about
	// Apple's iPhone 5s and add that object to the model.
	@RequestMapping("/products")
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
		// and finally we return the view name as 'products'
	}
	
	
	
	// these request mappings are on the controllers method level - "/products" and "/update/stock"
	@RequestMapping("/update/stock")
	public String updateStock(Model model) {
		productService.updateAllStock();
		return "redirect:/market/products";
	}
	
	
	
	// added from Chapter_3
	// By enclosing a portion of a request path within curly braces, we are indicating to Spring MVC that it is a URI template variable.
	// According to the Spring MVC documentation, a URI template is a URI-like string containing one or more variable names.
	// When you substitute values for these variables, the template becomes a URI. (http://localhost:8080/webstore/market/products/Laptop)
	// In Spring MVC, we can use the @PathVariable annotation to read a URI template variable.
	// A request mapping method can have any number of @PathVariable annotations.
	// It will be written like this: (@PathVariable("manufacturerId") String manufacturer, @PathVariable("productId") String product)
	@RequestMapping("/products/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
		List<Product> products = productService.getProductsByCategory(productCategory);
		
		if (products == null || products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
		}
		
		model.addAttribute("products", products);
		return "products";
	}
	
	
	
	// added from Chapter_3
	// A URL can have multiple matrix variables, and each matrix variable must be separated with a “;” (semicolon).
	// To assign multiple values to a single variable, each value must be “,” (comma) separated or we can repeat the variable name.
	// @MatrixVariable is very similar to the @PathVariable annotation.
	// The filterParams map will have each matrix variable name as the key and the corresponding list will contain multiple 
	// values assigned for the matrix variable.
	// The 'pathVar' attribute from @MatrixVariable is used to identify the matrix variable segment in the URL,
	// that's why it has the value params, which is nothing but the URI template value we used in our request mapping URL.
	// A URL can have multiple matrix variable segments, eg.:
	//	 /products/filter/params;brands=Google,Dell;categories=Tablet,Laptop/specification;dimention=10,20,15;color=red,green,blue
	// It contains two matrix variable segments each identified by the prefix params and specification respectively.
	@RequestMapping("/products/filter/{params}")
	public String getProductsByFilter(@MatrixVariable(pathVar="params") Map<String,List<String>> filterParams, Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}
	
	
	
	// added from Chapter_3
	// The @RequestParam annotation also follows the same convention as other binding annotations; that is, if the name of the 
	// GET request parameter and the name of the variable it is annotating are the same, then there is no need to specify the 
	// value attribute in the @RequestParam annotation. - (value = "")
	// Since we annotated the parameter productId with @RequestParam("id") annotation, Spring MVC will try to read a GET request
	// parameter with the name 'id' from the URL and will assign that to the getProductById method's parameter productId, eg.:
	//   /market/product?id=P1234
	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		return "product";
	}
	
	
	
	// added from Chapter_4
	// when we enter the URL - webstore/market/products/add - in the browser, it is considered as a GET request,
	// so Spring MVC will map that request to the 'getAddNewProductForm' method.
	// Within that method, we simply attach a new empty Product domain object with the model, under the attribute name newProduct.
	// So in the 'addproduct.jsp' View, we can access that newProduct Model object.
	@RequestMapping(value = "/products/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}
	
	
	
	// added from Chapter_4
	// This method will be invoked once we press the submit button on our form.
	// Since every form submission is considered a POST request, this time the browser will send a POST request to the same URL:
	//   http://localhost:8080/webstore/products/add.
	// We are simply calling the 'addProduct' service method to add the new product to the repository.
	// The @ModelAttribute annotation's value and the value of the modelAttribute from the <form:form> tag are the same.
	// So Spring MVC knows that it should assign the form bounded 'newProduct' object to the 'processAddNewProductForm'
	// method's 'productToBeAdded' parameter.
	// Then finally our logical View name, instead of returning a View name, we are simply instructing Spring to issue a redirect 
	// request to the request path /market/products, which is the request path for the list method of our ProductController.
	// So, after submitting the form, we list the products using the 'list' method of 'ProductController{}'.
	// We added the @Valid annotation in front of 'Product' in the method parameter from Chapter_8
	@RequestMapping(value = "/products/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product productToBeAdded, BindingResult result, HttpServletRequest request) {
		
		// added this if statement from Chapter_8
		if(result.hasErrors()) {
			return "addProduct";
		}
		
		String[] suppressedFields = result.getSuppressedFields();
		
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}
		
		// following 10 lines added from Chapter_5 and also the 'HttpServletRequest request' parameter above
		MultipartFile productImage = new Product().getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		
		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(rootDirectory+"resources\\images" + new Product().getProductId() + ".png"));
			}catch (Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
			}
		}
		
		
		productService.addProduct(productToBeAdded);
		return "redirect:/market/products";
	}
	
	
	
	// added from Chapter_4
	// The automatic binding feature of Spring could lead to a potential security vulnerability if we used a domain object itself as form bean.
	// The @InitBinder annotation designates a Controller method as a hook method to do some custom configuration regarding data binding on WebDataBinder.
	// And WebDataBinder is the thing that is doing the data binding at runtime, so we need to specify which fields are allowed to bind to WebDataBinder.
	// Here we are telling Spring MVC which area fields are allowed, and this is called "whitelisting".
	// This 'initialiseBinder' method has a parameter called 'binder', which is of the type WebDataBinder.
	// We are simply calling the 'setAllowedFields' method on the binder object and passing the field names that are allowed for binding.
	// Spring MVC will call this method to initialize WebDataBinder before doing the binding since it has the @InitBinder annotation.
	// WebDataBinder also has a method called 'setDisallowedFields' to strictly specify which fields are disallowed for binding, called "blacklisting".
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setAllowedFields("productId",
								"name",
								"unitPrice",
								"description",
								"manufacturer",
								"category",
								"unitsInStock",
								"condition",
								"productImage",		// Added from Chapter_5
								"language");		// Added from Chapter_6
		
		//binder.setValidator(unitsInStockValidator); // Added from Chapter_8
		
		binder.setValidator(productValidator);		// Added from Chapter_8, replaces the setValidator(unitsInStockValidator); above
	}
	
	
	
	// Added from Chapter_5
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidProductId", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
		mav.setViewName("productNotFound");
		return mav;
	}
	
	
	
	// Added from Chapter_6
	@RequestMapping("/products/invalidPromoCode")
	public String invalidPromoCode() {
		return "invalidPromoCode";
	}
	
	
	
	/*
	// Added from Chapter_8
	// We need to associate that validator with the controller, so we simply added and autowired the reference to 'UnitsInStockValidator'.
	// And we associated the unitsInStockValidator with WebDataBinder in the initialiseBinder method in this 'ProductController.java' class.
	@Autowired
	private UnitsInStockValidator unitsInStockValidator;
	*/
	
	
	
	// Added from Chapter_8
	// Replaced the existing reference of the 'UnitsInStockValidator' field above, with our newly created 'ProductValidator' class
	@Autowired
	private ProductValidator productValidator;
	
	
	
}












