
// this class as created from Chapter_7

package com.packt.webstore.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


// Spring should know about the security-related configuration file and will have to read this configuration file before booting the application.
// By extending the 'AbstractSecurityWebApplicationInitializer' class, we can instruct Spring MVC to pick up our 'SecurityConfig' class during boot-up.
// Only then can it create and manage those security-related configurations.
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	
	
}
