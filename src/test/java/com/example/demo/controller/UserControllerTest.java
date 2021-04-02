package com.example.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.TestUtils;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

public class UserControllerTest {
	private UserController userController = mock(UserController.class);
	private UserRepository userRepository = mock(UserRepository.class);
	private CartRepository cartRepository = mock(CartRepository.class);
	private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

	@Before
	public void setUp()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		userController = new UserController();
		TestUtils.injectObject(userController, "userRepository", userRepository);
		TestUtils.injectObject(userController, "cartRepository", cartRepository);
		TestUtils.injectObject(userController, "bCryptPasswordEncoder", encoder);
	}

	@Test
	public void createUser() {
		when(encoder.encode("mypass123")).thenReturn("thisIsHashed");

		CreateUserRequest r = new CreateUserRequest();
		r.setUsername("Nath");
		r.setPassword("mypass123");
		r.setConfirmPassword("mypass123");

		final ResponseEntity<User> response = userController.createUser(r);
		User u = response.getBody();

		assertNotNull(u);
		assertEquals("Nath", u.getUsername());
		assertEquals("thisIsHashed", u.getPassword());

	}

}
