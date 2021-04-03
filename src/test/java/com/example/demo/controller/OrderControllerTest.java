package com.example.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.example.demo.TestUtils;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

public class OrderControllerTest {
	private OrderController orderController = mock(OrderController.class);
	private UserRepository userRepository = mock(UserRepository.class);
	private OrderRepository orderRepository = mock(OrderRepository.class);


	@Before
	public void setUp()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		orderController = new OrderController();
		TestUtils.injectObject(orderController, "orderRepository", orderRepository);
		TestUtils.injectObject(orderController, "userRepository", userRepository);
	}
	
	@Test
	public void testGetAllItems() {
		// create new user
		User u1 = new User();
		u1.setUsername("Nath");
		u1.setId(0);
		
		// create new order
		UserOrder uo1 = new UserOrder();
		uo1.setTotal(new BigDecimal(1000));
		uo1.setUser(u1);
	
		when(userRepository.findByUsername(u1.getUsername())).thenReturn(u1);
		when(orderRepository.findByUser(u1)).thenReturn(Collections.nCopies(1, uo1));
		final ResponseEntity<List<UserOrder>> responseReturnedOrders = orderController.getOrdersForUser("Nath");
		List<UserOrder> o = responseReturnedOrders.getBody();

		// verify values
		assertNotNull(o);
		assertEquals(200, responseReturnedOrders.getStatusCodeValue());
		assertEquals(1, o.size());
		assertEquals(1000, o.get(0).getTotal().intValue());

	}
	
	
	@Test
	public void testSubmitOrder() {
		// create new item
		Item i1 = new Item();
		i1.setName("Widget");
		i1.setDescription("A widget");
		i1.setPrice(new BigDecimal(1000));
		
		// create new cart
		Cart c1 = new Cart();
		c1.addItem(i1);

		// create new user
		User u1 = new User();
		u1.setUsername("Nath");
		u1.setId(0);
		u1.setCart(c1);
		
		// create new order
		UserOrder uo1 = new UserOrder();
		uo1.setTotal(new BigDecimal(1000));
		uo1.setUser(u1);
	
		when(userRepository.findByUsername(u1.getUsername())).thenReturn(u1);
		when(orderRepository.save(uo1)).thenReturn(uo1);
		final ResponseEntity<UserOrder> responseReturnedOrder = orderController.submit(u1.getUsername());
		UserOrder order = responseReturnedOrder.getBody();

		// verify values
		assertNotNull(order);
		assertEquals(200, responseReturnedOrder.getStatusCodeValue());
		assertEquals(1000, order.getTotal().intValue());
		
	}
	
	
	
}
