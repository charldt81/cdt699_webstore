package com.packt.webstore.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;
import org.springframework.web.util.UrlPathHelper;

import com.packt.webstore.domain.Product;
import com.packt.webstore.interceptor.ProcessingTimeLogInterceptor;
import com.packt.webstore.interceptor.PromoCodeInterceptor;
import com.packt.webstore.validator.ProductValidator;
import com.packt.webstore.validator.UnitsInStockValidator;



@Configuration							// this indicates that a class declares one or more @Bean methods
@EnableWebMvc							// Adding this annotation to an @Configuration class imports some special Spring MVC configuration
@ComponentScan("com.packt.webstore")	// This specifies the base packages to scan for annotated components (beans)
public class WebApplicationContextConfig extends WebMvcConfigurerAdapter {

	
	@Override
	public void configureDefaultServletHandling (DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	
	// To enable the use of matrix variables in Spring MVC, we must set the RemoveSemicolonContent property of UrlPathHelper to false.
	// Here we are enabling matrix variable support by overriding the configurePathMatch method as follows:
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setRemoveSemicolonContent(false);
		configurer.setUrlPathHelper(urlPathHelper);
	}
	
	
	// This is a '@Bean' from the 'org.springframework' and is imported as shown above.
	// We are instructing Spring MVC to create a bean for the class - InternalResourceViewResolver.
	// We configured InternalResourceViewResolver as our view resolver in the web application context configuration, 
	// during the process of resolving the view file for the given view name (in our case the view name is products), 
	// the view resolver will try to look for a file called products.jsp under /WEB-INF/views/.
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	
	
	// Added from Chpater_4
	// We created a message source property file and added the <spring:message> tag in our JSP file, but to connect these two we need to 
	// create one more Spring bean in our web application context.
	// One important property you need to notice here is the 'basename' property; we assigned the value "messages" for that property.
	// That is all we did to enable the externalizing of messages in a JSP file.
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
		resource.setBasename("messages");
		return resource;
	}
	
	
	
	// Added from Chapter_5
	// Here we are telling Spring where the image files are located in our project, so that Spring can serve those files upon request.
	// The 'addResourceLocations' method from 'ResourceHandlerRegistry' defines the base directory location of the static resources that you want to serve.
	// The other method, 'addResourceHandler', just indicated the request path that needs to be mapped to this resource directory.
	// In our case, we assigned "/img/**" as the mapping value.
	// So if any web request comes with the request path /img, then it will be mapped to the 'resources/images' directory.
	// The '/**' symbol indicates to recursively look for any resource files underneath the base resource directory.
	// Remember Spring allows you to not only host images, but also any type of static files such as PDFs, Word documents, Excel sheets, and so in this fashion.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**").addResourceLocations("/resources/images/");
	}
	
	
	
	// Added from Chapter_5
	// Spring's 'CommonsMultipartResolver' class is the thing that determines whether the given request contains multipart content and
	// parses the given HTTP request into multipart files and parameters.
	// Through the 'setMaxUploadSize' property, we set a maximum of 10,240,000 bytes as the allowed file size to be uploaded.
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		resolver.setMaxUploadSize(10240000);
		return resolver;
	}
	
	
	
	// Added from Chapter_5
	@Bean
	public MappingJackson2JsonView jsonView() {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setPrettyPrint(true);
		return jsonView;
	}
	
	
	
	// Added from Chapter_5
	// The xmlView bean configuration especially has one important property to be set called 'classesToBeBound'; 
	// this lists the domain objects that require XML conversion during the request processing.
	// Since our product domain object requires XML conversion, we added 'com.packt.webstore.domain.Product' to the list 'classesToBeBound':
	// In order to convert it to XML, we need to give MarshallingView one more hint to identify the root XML element in the Product domain object.
	// We annotated our 'Product.java' class with the @XmlRootElement annotation.
	@Bean
	public MarshallingView xmlView() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Product.class);
		MarshallingView xmlView = new MarshallingView(marshaller);
		return xmlView;
	}
	
	
	
	// Added from Chapter_5
	// We want a view resolver to resolve XML and JSON Views, that's why we configured 'ContentNegotiatingViewResolver'.
	// ContentNegotiatingViewResolver does not resolve Views itself but rather it delegates to other Views based on the request,
	// so we need to introduce other Views to ContentNegotiatingViewResolver.
	// How we do that is through the 'setDefaultViews()' method in ContentNegotiatingViewResolver:
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		ArrayList<View> views = new ArrayList<>();
		views.add(jsonView());
		views.add(xmlView());
		resolver.setDefaultViews(views);
		return resolver;
	}
	
	
	
	// Added from Chapter_6
	// This is our InterceptorRegistry
	// We can specify URL patterns using the addPathPatterns method.
	// This way we can specify the URL patterns to which the registered interceptor should apply.
	// So our 'promoCodeInterceptor' will get executed only for a request that ends with market/specialOffer.
	// While adding an interceptor, we can also specify the URL patterns that the registered interceptor should not apply to via the 'excludePathPatterns' method.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ProcessingTimeLogInterceptor());
		
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);
		
		registry.addInterceptor(promoCodeInterceptor()).addPathPatterns("/**/market/products/specialOffer");
	}
	
	
	
	// Added from Chapter_6
	// SessionLocaleResolver is the one that sets the locale attribute in the user's session.
	// One important property of SessionLocaleResolver is defaultLocale, this indicates that by default or page should use English.
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(new Locale("en"));
		return resolver;
	}
	
	
	
	// Added from Chapter_6
	@Bean
	public HandlerInterceptor promoCodeInterceptor() {
		PromoCodeInterceptor promoCodeInterceptor = new PromoCodeInterceptor();
		promoCodeInterceptor.setPromoCode("OFF3R");
		promoCodeInterceptor.setOfferRedirect("market/products");
		promoCodeInterceptor.setErrorRedirect("invalidPromoCode");
		return promoCodeInterceptor;
	}
	
	
	
	// Added from Chapter_8
	// This 'LocalValidatorFactoryBean' will initiate the Hibernate Validator during the booting of our application.
	// The 'setValidationMessageSource()' method indicates which message source bean it should look to for error messages.
	// We already configured message sources in our web application context, we just use that bean, which is why we assigned the value
	// 'messageSource()' as the value for the 'setValidationMessageSource()' method.
	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	
	
	
	// Added from Chapter_8
	// Here we override the getValidator method to configure our validator bean as the default validator.
	@Override
	public Validator getValidator(){
		return validator();
	}
	
	
	
	// Added from Chapter_8
	@Bean
	public ProductValidator productValidator () {
		Set<Validator> springValidators = new HashSet<>();
		springValidators.add(new UnitsInStockValidator());
		ProductValidator productValidator = new ProductValidator();
		productValidator.setSpringValidators(springValidators);
		return productValidator;
	}
	
	
	
	
}














