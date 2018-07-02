
// This 'WebFlowConfig.java' class was added from Chapter_10

package com.packt.webstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;





@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

	
	// In this flow configuration, we created the 'flowDefinitionRegistry' bean, whose base-path is /WEB-INF/flows, so we need to put all 
	// our flow definitions under the /WEB-INF/flows directory in order to get picked up by the 'flowDefinitionRegistry'.
	@Bean
	public FlowDefinitionRegistry flowRegistry() {
		return getFlowDefinitionRegistryBuilder().setBasePath("/WEB-INF/flows")
												 .addFlowLocationPattern("/**/*-flow.xml")
												 .build();
	}
	
	
	
	@Bean
	public FlowExecutor flowExecutor() {
		return getFlowExecutorBuilder(flowRegistry()).build();
	}
	
	
	
	// 'flowHandlerMapping()' creates and configures handler mapping, based on the flow ID for each defined flow from flowRegistry.
	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
		handlerMapping.setOrder(-1);
		handlerMapping.setFlowRegistry(flowRegistry());
		return handlerMapping;
	}
	
	
	
	// 'flowHandlerAdapter()' acts as a bridge between the dispatcher servlet and Spring Web Flow, in order to execute the flow instances.
	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
		handlerAdapter.setFlowExecutor(flowExecutor());
		handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
		return handlerAdapter;
	}
	
	
	
	
}















