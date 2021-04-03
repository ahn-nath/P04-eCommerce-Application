package com.example.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.example.demo.TestUtils;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

public class ItemControllerTest {
	private ItemController itemController = mock(ItemController.class);
	private ItemRepository itemRepository = mock(ItemRepository.class);

	@Before
	public void setUp()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		itemController = new ItemController();
		TestUtils.injectObject(itemController, "itemRepository", itemRepository);

	}

	@Test
	public void testGetAllItems() {
		// create new item
		Item i1 = new Item();
		i1.setName("Square Widget");
		i1.setDescription("A widget that is square");
		i1.setPrice(new BigDecimal(1.99));

		when(itemRepository.findAll()).thenReturn(Collections.nCopies(2, i1));
		final ResponseEntity<List<Item>> responseReturnedItems = itemController.getItems();
		List<Item> i = responseReturnedItems.getBody();

		// verify values
		assertNotNull(i);
		assertEquals(200, responseReturnedItems.getStatusCodeValue());
		assertEquals(2, i.size());

	}

	@Test
	public void testFindItemById() {
		long itemId = 1;

		// create new item
		Item i1 = new Item();
		i1.setName("Square Widget");
		i1.setDescription("A widget that is square");
		i1.setPrice(new BigDecimal(1.99));

		when(itemRepository.findById(itemId)).thenReturn(Optional.of(i1));
		final ResponseEntity<Item> responseReturnedItem = itemController.getItemById(itemId);
		Item i = responseReturnedItem.getBody();

		// verify values
		assertNotNull(i);
		assertEquals("Square Widget", i.getName());
		assertEquals("A widget that is square", i.getDescription());

	}

	@Test
	public void testGetAllItemsByName() {
		List<Item> items = new ArrayList<>();

		// create new item
		Item i1 = new Item();
		i1.setName("Square Widget");
		i1.setDescription("A widget that is square");
		i1.setPrice(new BigDecimal(1.99));

		items.add(i1);

		when(itemRepository.findByName("Square Widget")).thenReturn(items);

		final ResponseEntity<List<Item>> responseReturnedItems = itemController.getItemsByName(i1.getName());
		List<Item> i = responseReturnedItems.getBody();

		// verify values
		assertNotNull(i);
		assertEquals(200, responseReturnedItems.getStatusCodeValue());
		assertEquals("Square Widget", i.get(0).getName());
		assertEquals(1, i.size());

	}

}
