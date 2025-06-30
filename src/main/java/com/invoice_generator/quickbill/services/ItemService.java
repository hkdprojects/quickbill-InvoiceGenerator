package com.invoice_generator.quickbill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoice_generator.quickbill.entity.Item;
import com.invoice_generator.quickbill.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Create or Update
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    // Read all
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Read one by ID
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item not found with ID: " + itemId));
    }
    

    // Delete
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
