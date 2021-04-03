package com.example.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
	public void testCreateUser() {
		when(encoder.encode("mypass123")).thenReturn("thisIsHashed");

		// create user
		final ResponseEntity<User> responseCreateuser = createNewUser();
		User u = responseCreateuser.getBody();

		// verify values
		assertNotNull(u);
		assertEquals("Nath", u.getUsername());
		assertEquals("thisIsHashed", u.getPassword());

	}


	@Test
	public void testFindUserById() throws Exception {
		// create new user
		final ResponseEntity<User> createdUserResponse = createNewUser();
		assertNotNull(createdUserResponse);
		assertEquals(200, createdUserResponse.getStatusCodeValue());

		User u1 = createdUserResponse.getBody();
		assertNotNull(u1);
		assertEquals(0, u1.getId());
		assertEquals("Nath", u1.getUsername());
		assertEquals("thisIsHashed", u1.getPassword());

		// find user by id
		when(userRepository.findById(u1.getId())).thenReturn(Optional.of(u1));
		final ResponseEntity<User> findUserById = userController.findById(u1.getId());
		User u2 = findUserById.getBody();
		assertEquals(200, findUserById.getStatusCodeValue());
		assertEquals(0, u2.getId());
		assertEquals("Nath", u2.getUsername());
		assertEquals("thisIsHashed", u2.getPassword());
	}
	
	@Test
	public void testFindUserName() throws Exception {
		// create new user
		final ResponseEntity<User> createdUserResponse = createNewUser();
		assertNotNull(createdUserResponse);
		assertEquals(200, createdUserResponse.getStatusCodeValue());

		User u1 = createdUserResponse.getBody();
		assertNotNull(u1);
		assertEquals(0, u1.getId());
		assertEquals("Nath", u1.getUsername());
		assertEquals("thisIsHashed", u1.getPassword());

		// find user by id
		when(userRepository.findByUsername(u1.getUsername())).thenReturn(u1);
		final ResponseEntity<User> findUserByUsername = userController.findByUserName(u1.getUsername());
		User u2 = findUserByUsername.getBody();
		assertEquals(200, findUserByUsername.getStatusCodeValue());
		assertEquals(0, u2.getId());
		assertEquals("Nath", u2.getUsername());
		assertEquals("thisIsHashed", u2.getPassword());
	}

	// helper methods
	public ResponseEntity<User> createNewUser() {
		when(encoder.encode("mypass123")).thenReturn("thisIsHashed");

		CreateUserRequest userRequest = new CreateUserRequest();
		userRequest.setUsername("Nath");
		userRequest.setPassword("mypass123");
		userRequest.setConfirmPassword("mypass123");

		final ResponseEntity<User> response = userController.createUser(userRequest);
		return response;
	}

}
