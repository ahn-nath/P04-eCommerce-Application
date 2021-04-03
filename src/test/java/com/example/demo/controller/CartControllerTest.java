package com.example.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

public class CartControllerTest {
	private CartController cartController = mock(CartController.class);
	private UserRepository userRepository = mock(UserRepository.class);
	private ItemRepository itemRepository = mock(ItemRepository.class);
	private CartRepository cartRepository = mock(CartRepository.class);

	@Before
	public void setUp()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		cartController = new CartController();
		TestUtils.injectObject(cartController, "userRepository", userRepository);
		TestUtils.injectObject(cartController, "itemRepository", itemRepository);
		TestUtils.injectObject(cartController, "cartRepository", cartRepository);
	}

	@Test
	public void testAddToCart() {
		// create new item
		Item i1 = new Item();
		i1.setName("Widget");
		i1.setDescription("A widget");
		i1.setPrice(new BigDecimal(1000));
		i1.setId((long) 1);

		// create new cart
		Cart c1 = new Cart();
		c1.addItem(i1);
		c1.setId((long) 1);

		// create new user
		User u1 = new User();
		u1.setUsername("Nath");
		u1.setId(0);
		u1.setCart(c1);

		// create CreateUserRequest
		ModifyCartRequest cr1 = new ModifyCartRequest();
		cr1.setItemId(i1.getId());
		cr1.setUsername(u1.getUsername());

		when(userRepository.findByUsername(u1.getUsername())).thenReturn(u1);
		when(itemRepository.findById(i1.getId())).thenReturn(Optional.of(i1));

		final ResponseEntity<Cart> responseReturnedCart = cartController.addToCart(cr1);
		Cart cart = responseReturnedCart.getBody();

		// verify values
		assertNotNull(cart);
		assertEquals(200, responseReturnedCart.getStatusCodeValue());

	}

	@Test
	public void testRemoveFromCart() {
		// create new item
		Item i1 = new Item();
		i1.setName("Widget");
		i1.setDescription("A widget");
		i1.setPrice(new BigDecimal(1000));
		i1.setId((long) 1);

		// create new cart
		Cart c1 = new Cart();
		c1.addItem(i1);
		c1.setId((long) 1);

		// create new user
		User u1 = new User();
		u1.setUsername("Nath");
		u1.setId(0);
		u1.setCart(c1);

		// create CreateUserRequest
		ModifyCartRequest cr1 = new ModifyCartRequest();
		cr1.setItemId(i1.getId());
		cr1.setUsername(u1.getUsername());

		when(userRepository.findByUsername(u1.getUsername())).thenReturn(u1);
		when(itemRepository.findById(i1.getId())).thenReturn(Optional.of(i1));

		final ResponseEntity<Cart> responseReturnedCart = cartController.removeFromCart(cr1);
		Cart cart = responseReturnedCart.getBody();

		// verify values
		assertNotNull(cart);
		assertEquals(200, responseReturnedCart.getStatusCodeValue());

	}
}
