
// this 'OrderRepository.java' interface was added from Chapter_10

package com.packt.webstore.domain.repository;

import com.packt.webstore.domain.Order;



public interface OrderRepository {
	
	
	long saveOrder(Order order);
	

}
