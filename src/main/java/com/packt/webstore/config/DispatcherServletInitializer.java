package com.packt.webstore.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;



public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootApplicationContextConfig.class };	// this is one of the other Java classes that was created
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebApplicationContextConfig.class };	// this is one of the other Java classes that was created
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };								// this indicates what the URL starts with 
	}

	
	
	
}
