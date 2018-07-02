
// this class was added from Chapter_7

package com.packt.webstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



// We are simply creating a class called 'SecurityConfig' to configure the security-related aspects of our web application.
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	// Under this method we are simply configuring 'AuthenticationManagerBuilder' to create two users, john and admin, with a specified password and roles
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("john").password("pa55word").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("root123").roles("USER","ADMIN");
	}
	
	
	
	// Within this method we are doing some authentication and authorization-related configuration and we will see these one by one.
	// The first configuration tells Spring MVC that it should redirect the users to the login page if authentication is required, here the 'loginPage' attribute 
	// denotes to which URL it should forward the request to get the login form.
	// Remember this request path should be the same as the request mapping of the login() method of LoginController.
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.formLogin().loginPage("/login")
								.usernameParameter("userId")
								.passwordParameter("password");
		
		httpSecurity.formLogin().defaultSuccessUrl("/market/products/add")
								.failureUrl("/login?error");
		
		httpSecurity.logout().logoutSuccessUrl("/login?logout");
		
		httpSecurity.exceptionHandling().accessDeniedPage("/login?accessDenied");
		
		httpSecurity.authorizeRequests().antMatchers("/")
										.permitAll()
										.antMatchers("/**/add").access("hasRole('ADMIN')")
										.antMatchers("/**/market/**").access("hasRole('USER')");
		
		httpSecurity.csrf().disable();
	}
	
	
	
}
