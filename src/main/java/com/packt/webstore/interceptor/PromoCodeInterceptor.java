
// this class was added from Chapter_6

package com.packt.webstore.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



public class PromoCodeInterceptor extends HandlerInterceptorAdapter {
	
	
	// The 'promoCode' property is used to configure the valid promo code; so whoever is accessing the special-offer page should provide the promo code
	// in their HTTP parameter in order to access the special-offer page.
	private String promoCode;
	
	// The next two attributes, 'errorRedirect' and 'offerRedirect', are used for redirection.
	// 'errorRedirect' indicates the redirect URL mapping in the case of an invalid promo code and 'offerRedirect' indicates the redirect URL mapping for 
	// a successful promo code redirection.
	private String errorRedirect;
	private String offerRedirect;
	
	
	
	// In the preHandle method, we are simply checking whether the request contains the correct promo code as the HTTP parameter.
	// If so, we redirect the request to the configured special offer page; otherwise, we redirect it to the configured error page.
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		String givenPromoCode = request.getParameter("promo");
		
		if (promoCode.equals(givenPromoCode)) {
			response.sendRedirect(request.getContextPath() + "/" + offerRedirect);
		} else {
			response.sendRedirect(errorRedirect);
		}
		
		return false;
	}
	
	
	
	
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	
	
	
	public void setErrorRedirect(String errorRedirect) {
		this.errorRedirect = errorRedirect;
	}
	
	
	
	public void setOfferRedirect(String offerRedirect) {
		this.offerRedirect = offerRedirect;
	}

}
