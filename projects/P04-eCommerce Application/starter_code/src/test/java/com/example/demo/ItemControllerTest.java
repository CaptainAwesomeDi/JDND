package com.example.demo;

import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private ItemController itemController;
    private List<Item> items;

    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
        Item item1 = new Item();
        item1.setId(0L);
        item1.setName("item same name");
        item1.setPrice(BigDecimal.valueOf(0.1));
        item1.setDescription("Item Code 0");

        Item item2 = new Item();
        item2.setId(1L);
        item2.setName("item same name");
        item2.setPrice(BigDecimal.valueOf(10));
        item2.setDescription("Item Code 1");

        items = Arrays.asList(item1, item2);
    }

    @Test
    public void getAllItems() {
        when(itemRepository.findAll()).thenReturn(items);
        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(items.toArray(), response.getBody().toArray(new Item[0]));
    }

    @Test
    public void getItemById() {
        when(itemRepository.findById(items.get(0).getId()))
                .thenReturn(Optional.of(items.get(0)));
        ResponseEntity<Item> response = itemController.getItemById(0L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(items.get(0),response.getBody());
    }

    @Test
    public void getItemsByName() {
        when(itemRepository.findByName("item same name")).thenReturn(items);
        final ResponseEntity<List<Item>> response = itemController.getItemsByName("item same name");
        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(items.toArray(), response.getBody().toArray(new Item[0]));
    }
}
