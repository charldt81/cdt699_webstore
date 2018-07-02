
// this class was added from Chapter_7

package com.packt.webstore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class LoginController {

	
	// We created one more controller called LoginController to handle all login related web requests.
	// It simply contains a single request mapping method to handle login, login failure and log out requests.
	// We have created a controller (LoginController) to dispatch the login page.
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	
}
